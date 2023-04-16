package cz.zcu.students.lostandfound.features.lost_items.domain.lost_item

import android.net.Uri
import cz.zcu.students.lostandfound.common.features.map.domain.location_coordinates.LocationCoordinates
import java.util.*

data class LostItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val postOwnerId: String,
    val description: String,
    val isFound: Boolean = false,
    val isDeleted: Boolean = false,
    val createdAt: Long? = null,
    val imageUri: Uri? = null,
    val location: LocationCoordinates? = null,
    val itemOwnerId: String? = null,
)
