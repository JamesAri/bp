package cz.zcu.students.lostandfound.features.lost_items.domain.repository

import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItemList
import kotlinx.coroutines.flow.Flow

interface LostItemRepository {
    suspend fun getLostItemListFlow(): Response<Flow<LostItemList>>

    suspend fun getLostItem(id: String): Response<LostItem>

    suspend fun createLostItem(lostItem: LostItem): Response<Boolean>

    suspend fun updateLostItem(lostItem: LostItem): Response<Boolean>

    suspend fun getLostItemListFlowFromCurrentUser():  Response<Flow<LostItemList>>
}