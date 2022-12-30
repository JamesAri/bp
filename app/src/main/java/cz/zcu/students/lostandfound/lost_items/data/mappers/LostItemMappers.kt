package cz.zcu.students.lostandfound.lost_items.data.mappers

import cz.zcu.students.lostandfound.common.Constants.Companion.NO_VALUE
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemDto
import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem


fun LostItemDto.toLostItem(): LostItem {
    return LostItem(
        id = id,
        title = title ?: NO_VALUE,
        description = description ?: NO_VALUE,
        location = LocationCoordinates(0.0, 0.0), // todo
    )
}

fun LostItem.toLostItemDto(): LostItemDto {
    return LostItemDto(
        id = id,
        title = title,
        description = description,
        location = NO_VALUE, // todo
    )
}