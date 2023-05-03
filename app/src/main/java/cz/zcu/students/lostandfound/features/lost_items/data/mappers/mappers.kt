package cz.zcu.students.lostandfound.features.lost_items.data.mappers

import android.net.Uri
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemDto
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Maps lost item DTO [LostItemDto] to the domain [LostItem] object.
 *
 * @return domain [LostItem] object.
 */
fun LostItemDto.toLostItem(): LostItem {
    if (title.isNull()) throw Exception("missing title for lost item")
    if (description.isNull()) throw Exception("missing description for lost item")
    if (postOwnerId.isNull()) throw Exception("missing post owner id for lost item")
    if (isFound.isNull()) throw Exception("missing 'is found' for lost item")
    if (isDeleted.isNull()) throw Exception("missing 'is deleted' for lost item")

    val deserializedLocation = location?.let {
        Json.decodeFromString<LocationCoordinates>(it)
    }

    return LostItem(
        id = id,
        title = title!!,
        description = description!!,
        isFound = isFound!!,
        isDeleted = isDeleted!!,
        imageUri = imageUri?.let { Uri.parse(it) },
        location = deserializedLocation,
        createdAt = createdAt?.seconds,
        postOwnerId = postOwnerId!!,
        itemOwnerId = itemOwnerId,
    )
}

/**
 * Maps domain [LostItem] object to the DTO [LostItemDto].
 *
 * @return DTO object [LostItemDto].
 */
fun LostItem.toLostItemDto(): LostItemDto {
    val serializedLocation = location?.let {
        Json.encodeToString(it)
    }

    return LostItemDto(
        id = id,
        title = title,
        description = description,
        isFound = isFound,
        isDeleted = isDeleted,
        location = serializedLocation,
        imageUri = imageUri?.toString(),
        postOwnerId = postOwnerId,
        itemOwnerId = itemOwnerId,
    )
}