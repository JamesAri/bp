package cz.zcu.students.lostandfound.lost_items.domain.repository

import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface LostItemRepository {
    suspend fun getLostItemListFlow(): Resource<Flow<List<LostItem>>>

    suspend fun getLostItem(id: String): Resource<LostItem>

    suspend fun createLostItem(lostItem: LostItem, callback: (String) -> Unit)
}