package cz.zcu.students.lostandfound.features.lost_items.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_CREATED_AT_KEY
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_DESCRIPTION_KEY
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_LOCATION_KEY
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_TITLE_KEY
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_URL_KEY
import java.util.*

data class LostItemDto(
    @get:DocumentId
    val id: String = UUID.randomUUID().toString(),

    @get:PropertyName(LOST_ITEM_TITLE_KEY)
    @set:PropertyName(LOST_ITEM_TITLE_KEY)
    var title: String? = null,

    @get:PropertyName(LOST_ITEM_DESCRIPTION_KEY)
    @set:PropertyName(LOST_ITEM_DESCRIPTION_KEY)
    var description: String? = null,

    @get:PropertyName(LOST_ITEM_CREATED_AT_KEY)
    @set:PropertyName(LOST_ITEM_CREATED_AT_KEY)
    @ServerTimestamp
    var createdAt: Timestamp? = null,

    @get:PropertyName(LOST_ITEM_URL_KEY)
    @set:PropertyName(LOST_ITEM_URL_KEY)
    var imageUrl: String? = null,

    @get:PropertyName(LOST_ITEM_LOCATION_KEY)
    @set:PropertyName(LOST_ITEM_LOCATION_KEY)
    var location: String? = null,
)