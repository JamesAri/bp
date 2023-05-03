package cz.zcu.students.lostandfound.features.lost_items.domain.model

/**
 * Domain layer representation of lost items list.
 *
 * @property lostItems
 */
data class LostItemList(
    val lostItems: List<LostItem>
)
