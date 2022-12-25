package cz.zcu.students.lostandfound.lost_items.domain.lost_item

import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_ID_UNINITIALIZED_KEY
import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates

data class LostItem(
    val id: String = LOST_ITEM_ID_UNINITIALIZED_KEY,
    val title: String,
    val description: String,
    val location: LocationCoordinates,
)
