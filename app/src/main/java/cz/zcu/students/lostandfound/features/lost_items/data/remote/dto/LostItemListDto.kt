package cz.zcu.students.lostandfound.features.lost_items.data.remote.dto

/**
 * DTO for list of lost items.
 *
 * @property lostItems lost items DTO list.
 */
data class LostItemListDto(
    val lostItems: List<LostItemDto>,
)
