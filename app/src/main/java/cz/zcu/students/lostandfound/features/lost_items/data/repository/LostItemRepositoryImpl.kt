package cz.zcu.students.lostandfound.features.lost_items.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.Error
import cz.zcu.students.lostandfound.common.util.Response.Success
import cz.zcu.students.lostandfound.features.lost_items.data.mappers.toLostItem
import cz.zcu.students.lostandfound.features.lost_items.data.mappers.toLostItemDto
import cz.zcu.students.lostandfound.features.lost_items.data.remote.LostItemApi
import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemListDto
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of lost items repository [LostItemRepository].
 *
 * @property api lost items API [LostItemApi]
 */
@Singleton
class LostItemRepositoryImpl @Inject constructor(
    private val api: LostItemApi,
) : LostItemRepository {

    /** Instance of Firebase auth. */
    private val authInstance = FirebaseAuth.getInstance()

    /**
     * Maps DTO object [LostItemListDto] to [LostItemList] from domain layer.
     *
     * @param fetchLostItemListDto function with emitting lost item lists.
     * @return domain layer [LostItemList] with recent (up-to-date) lost items.
     */
    private suspend fun mapLostItemListDto(
        fetchLostItemListDto: suspend () -> Flow<LostItemListDto>
    ): Response<Flow<LostItemList>> {
        return try {
            val lostItemListDtoFlow = fetchLostItemListDto()
            withContext(Dispatchers.Default) {
                val lostItemListFlow: Flow<LostItemList> = lostItemListDtoFlow
                    .map { lostItemListDto ->
                        val lostItems = lostItemListDto.lostItems
                            .mapNotNull { item ->
                                try {
                                    item.toLostItem()
                                } catch (e: Exception) {
                                    Log.e(
                                        "LostItemRepositoryImpl",
                                        "getLostItemListFlow: ${e.message}"
                                    )
                                    null
                                }
                            }
                        LostItemList(lostItems)
                    }
                Success(lostItemListFlow)
            }
        } catch (e: Exception) {
            Error(e)
        }
    }


    override suspend fun getLostItemListFlow(
    ): Response<Flow<LostItemList>> {
        return mapLostItemListDto(fetchLostItemListDto = { api.getLostItemList() })
    }

    override suspend fun getLostItemListFlowFromCurrentUser(): Response<Flow<LostItemList>> {
        val id = authInstance.currentUser?.uid ?: return Error(Exception("not logged in"))
        return mapLostItemListDto(fetchLostItemListDto = { api.getLostItemListByOwnerId(id) })
    }


    override suspend fun getLostItem(id: String): Response<LostItem> {
        return try {
            val lostItem: LostItem? = api.getLostItem(id)?.toLostItem()

            if (lostItem.isNull())
                return Error(Exception("No such item found"))

            Success(lostItem)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun createLostItem(lostItem: LostItem): Response<Boolean> {
        return try {
            api.createLostItem(lostItem.toLostItemDto())
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun updateLostItem(lostItem: LostItem): Response<Boolean> {
        return try {
            api.updateLostItem(lostItem.toLostItemDto())
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }
}