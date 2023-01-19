package cz.zcu.students.lostandfound.features.lost_items.data.mappers

import android.net.Uri
import cz.zcu.students.lostandfound.common.constants.General.Companion.NO_VALUE
import cz.zcu.students.lostandfound.features.lost_items.data.remote.dto.LostItemDto
import cz.zcu.students.lostandfound.features.lost_items.domain.location.LocationCoordinates
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem

// TODO

fun LostItemDto.toLostItem(): LostItem {
    return LostItem(
        id = id,
        title = title ?: NO_VALUE,
        description = description ?: NO_VALUE,
        imageUri = imageUrl?.let { Uri.parse(it) },
        location = LocationCoordinates(0.0, 0.0), // todo
        createdAt = createdAt?.toString(),
    )
}

fun LostItem.toLostItemDto(): LostItemDto {
    return LostItemDto(
        id = id,
        title = title,
        description = description,
        location = location?.toString(),
        imageUrl = imageUri?.toString(),
    )
}