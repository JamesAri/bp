package cz.zcu.students.lostandfound.features.lost_items.domain.lost_item

import android.net.Uri
import cz.zcu.students.lostandfound.common.constants.General.Companion.ITEM_DESCRIPTION_DEFAULT
import cz.zcu.students.lostandfound.features.lost_items.domain.location.LocationCoordinates
import java.util.*

data class LostItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = ITEM_DESCRIPTION_DEFAULT,
    val createdAt: String? = null,
    val imageUri: Uri? = null,
    val location: LocationCoordinates? = null,
)
