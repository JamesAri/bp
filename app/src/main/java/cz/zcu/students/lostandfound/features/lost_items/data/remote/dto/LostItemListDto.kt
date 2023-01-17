package cz.zcu.students.lostandfound.features.lost_items.data.remote.dto

import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemDto

data class LostItemListDto(
    val lostItems: List<LostItemDto>,
)
