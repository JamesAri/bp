package cz.zcu.students.lostandfound.lost_items.data.remote

data class LostItemDto(
    val id: String,
    val title: String?,
    val description: String?,
    val location: String?
)