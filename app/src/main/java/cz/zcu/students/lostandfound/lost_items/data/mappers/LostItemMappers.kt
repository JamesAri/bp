package cz.zcu.students.lostandfound.lost_items.data.mappers

import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_DESCRIPTION_KEY
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_LOCATION_KEY
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_TITLE_KEY
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.NO_VALUE
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemDto
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemListDto
import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem


fun LostItemListDto.toLostItemList(): List<LostItem> {
    return lostItems.map {
        it.toLostItem()
    }
}

fun LostItemDto.toLostItem(): LostItem {
    return LostItem(
        id = id,
        title = title ?: NO_VALUE,
        description = description ?: NO_VALUE,
        location = LocationCoordinates(0.0, 0.0), // todo
    )
}

fun LostItemDto.toHashMap(): HashMap<String, Any?> {
    return hashMapOf(
        LOST_ITEM_TITLE_KEY to title,
        LOST_ITEM_DESCRIPTION_KEY to description,
        LOST_ITEM_LOCATION_KEY to location,
    )
}

fun LostItem.toLostItemDto(): LostItemDto {
    return LostItemDto(
        id = id,
        title = title.ifBlank { null },
        description = description.ifBlank { null },
        location = NO_VALUE, // todo
    )
}