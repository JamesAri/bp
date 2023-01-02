package cz.zcu.students.lostandfound.lost_items.data.mappers

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import cz.zcu.students.lostandfound.common.Constants.Companion.NO_VALUE
import cz.zcu.students.lostandfound.lost_items.data.remote.dto.LostItemDto
import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import java.time.LocalDate
import java.util.*

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
//        createdAt = LocalDate.parse(),
        imageUrl = imageUri?.toString(),
    )
}