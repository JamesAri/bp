package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.presentation.util.ResourceState

data class LostItemsState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class LostItemState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
