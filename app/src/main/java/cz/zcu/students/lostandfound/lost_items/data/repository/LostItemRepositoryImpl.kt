package cz.zcu.students.lostandfound.lost_items.data.repository

import cz.zcu.students.lostandfound.lost_items.data.mappers.toLostItem
import cz.zcu.students.lostandfound.lost_items.data.mappers.toLostItemDto
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemApi
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemRepository
import cz.zcu.students.lostandfound.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LostItemRepositoryImpl @Inject constructor(
    private val api: LostItemApi
) : LostItemRepository {

    override suspend fun getLostItemListFlow(): Resource<Flow<List<LostItem>>> {
        return try {
            Resource.Success(
                data = api.getLostItemListFlow().map {
                    it.lostItems.map { item ->
                        item.toLostItem()
                    }
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getLostItem(id: String): Resource<LostItem> {
        return try {
            Resource.Success(
                data = api.getLostItem(id).toLostItem()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun createLostItem(
        lostItem: LostItem,
        callback: (String) -> Unit
    ) {
        api.createLostItem(lostItem.toLostItemDto(), callback)
    }
}