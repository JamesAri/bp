package cz.zcu.students.lostandfound.lost_items.data.repository

import cz.zcu.students.lostandfound.lost_items.data.mappers.toLostItem
import cz.zcu.students.lostandfound.lost_items.data.mappers.toLostItemDto
import cz.zcu.students.lostandfound.lost_items.data.mappers.toLostItemList
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemApi
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemRepository
import cz.zcu.students.lostandfound.lost_items.domain.util.Resource
import javax.inject.Inject


class LostItemRepositoryImpl @Inject constructor(
    private val api: LostItemApi
) : LostItemRepository {

    override suspend fun getLostItemList(): Resource<List<LostItem>> {
        return try {
            Resource.Success(
                data = api.getLostItemList().toLostItemList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getLostItem(id: Int): Resource<LostItem> {
        return try {
            Resource.Success(
                data = api.getLostItem(id).toLostItem()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun createLostItem(lostItem: LostItem): Unit {
        api.createLostItem(lostItem.toLostItemDto())
    }
}