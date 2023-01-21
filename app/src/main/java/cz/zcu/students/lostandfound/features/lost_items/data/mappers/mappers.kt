package cz.zcu.students.lostandfound.features.lost_items.data.mappers

import android.net.Uri
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemDto
import cz.zcu.students.lostandfound.features.lost_items.domain.location.LocationCoordinates
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem


fun LostItemDto.toLostItem(): LostItem {
    if (title.isNull()) throw Exception("missing title for lost item")
    if (description.isNull()) throw Exception("missing description for lost item")
    if (postOwnerId.isNull()) throw Exception("missing post owner id for lost item")
    if (isFound.isNull()) throw Exception("missing 'is found' for lost item")

    return LostItem(
        id = id,
        title = title!!,
        description = description!!,
        isFound = isFound!!,
        imageUri = imageUri?.let { Uri.parse(it) },
        location = LocationCoordinates(0.0, 0.0), // todo
        createdAt = createdAt?.seconds,
        postOwnerId = postOwnerId!!,
        itemOwnerId = itemOwnerId,
    )
}

fun LostItem.toLostItemDto(): LostItemDto {
    return LostItemDto(
        id = id,
        title = title,
        description = description,
        isFound = isFound,
        location = location?.toString(),
        imageUri = imageUri?.toString(),
        postOwnerId = postOwnerId,
        itemOwnerId = itemOwnerId,
    )
}