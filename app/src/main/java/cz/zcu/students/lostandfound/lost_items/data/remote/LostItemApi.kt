package cz.zcu.students.lostandfound.lost_items.data.remote

interface LostItemApi {
    suspend fun getLostItemList(): LostItemListDto
    suspend fun getLostItem(id: Int): LostItemDto
    suspend fun createLostItem(lostItem: LostItemDto)
}