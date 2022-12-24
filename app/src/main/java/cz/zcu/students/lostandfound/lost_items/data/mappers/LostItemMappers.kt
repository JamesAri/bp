package cz.zcu.students.lostandfound.lost_items.data.mappers

import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_DESCRIPTION
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_TITLE
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemDto
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemListDto
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem


fun LostItemListDto.toLostItemList(): List<LostItem> {
    return lostItems.map {
        it.toLostItem()
    }
}

fun LostItemDto.toLostItem(): LostItem {
    return LostItem(
        title = data[LOST_ITEM_TITLE].toString(),
        description = data[LOST_ITEM_DESCRIPTION].toString()
    )
}

fun LostItem.toLostItemDto(): LostItemDto {
    return LostItemDto(
        data = hashMapOf(
            LOST_ITEM_TITLE to title,
            LOST_ITEM_DESCRIPTION to description,
        )
    )
}