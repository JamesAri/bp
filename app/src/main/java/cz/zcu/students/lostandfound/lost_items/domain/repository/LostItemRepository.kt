package cz.zcu.students.lostandfound.lost_items.domain.repository

import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.util.Resource

interface LostItemRepository {
    suspend fun getLostItemList(): Resource<List<LostItem>>
    suspend fun getLostItem(id: Int): Resource<LostItem>
    suspend fun createLostItem(lostItem: LostItem): Unit
}