package cz.zcu.students.lostandfound.features.lost_items.data.repository

import cz.zcu.students.lostandfound.common.constants.General.Companion.NO_ITEM_FOUND_ERR
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.features.lost_items.data.mappers.toLostItem
import cz.zcu.students.lostandfound.features.lost_items.data.mappers.toLostItemDto
import cz.zcu.students.lostandfound.features.lost_items.data.remote.LostItemApi
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.Error
import cz.zcu.students.lostandfound.common.util.Response.Success
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItemList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LostItemRepositoryImpl @Inject constructor(
    private val api: LostItemApi
) : LostItemRepository {

    override suspend fun getLostItemListFlow(
    ): Response<Flow<LostItemList>> {
        return try {
            val lostItemList: Flow<LostItemList> = api
                .getLostItemList()
                .map {
                    val list = it.lostItems.map { item ->
                        item.toLostItem()
                    }
                    LostItemList(list)
                }
            Success(lostItemList)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getLostItem(
        id: String
    ): Response<LostItem> {
        return try {
            val lostItem: LostItem? = api.getLostItem(id)?.toLostItem()

            if (lostItem.isNull())
                return Error(Exception(NO_ITEM_FOUND_ERR))

            Success(lostItem)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun createLostItem(
        lostItem: LostItem,
    ): Response<Boolean> {
        return try {
            api.createLostItem(lostItem.toLostItemDto())
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }
}