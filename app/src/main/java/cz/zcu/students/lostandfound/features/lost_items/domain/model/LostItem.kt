package cz.zcu.students.lostandfound.features.lost_items.domain.model

import android.net.Uri
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import java.util.*

/**
 * Domain layer representation of the lost item.
 *
 * @property id lost item id.
 * @property title title of lost item.
 * @property postOwnerId id of the user who is owner of this post.
 * @property description description of lost item.
 * @property isFound determines if lost item was returned to the owner.
 * @property isDeleted determines if the post about this item is deleted.
 * @property createdAt post creation time.
 * @property imageUri uri of the lost item image.
 * @property location location where the lost item was found.
 * @property itemOwnerId id of the user who is owner of this lost item,
 *     which means this user has probably retrieved the lost item.
 */
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
