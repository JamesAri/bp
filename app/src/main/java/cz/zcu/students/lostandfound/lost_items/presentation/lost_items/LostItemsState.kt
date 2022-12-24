package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem

data class LostItemsState(
    val lostItems: List<LostItem>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class LostItemState(
    val lostItem: LostItem? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
