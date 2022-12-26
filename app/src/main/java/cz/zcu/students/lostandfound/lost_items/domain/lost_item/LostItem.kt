package cz.zcu.students.lostandfound.lost_items.domain.lost_item

import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates
import java.util.*

data class LostItem(
    val id: String = "lostItems_" + UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val location: LocationCoordinates,
)
