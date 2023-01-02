package cz.zcu.students.lostandfound.lost_items.domain.lost_item

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import cz.zcu.students.lostandfound.common.Constants.Companion.ITEM_DESCRIPTION_DEFAULT
import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates
import java.util.*

data class LostItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = ITEM_DESCRIPTION_DEFAULT,
    val createdAt: String? = null,
    val imageUri: Uri? = null,
    val location: LocationCoordinates? = null,
)
