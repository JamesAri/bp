package cz.zcu.students.lostandfound.lost_items.data.remote

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import cz.zcu.students.lostandfound.common.Constants.Companion.LOST_ITEM_DESCRIPTION_KEY
import cz.zcu.students.lostandfound.common.Constants.Companion.LOST_ITEM_LOCATION_KEY
import cz.zcu.students.lostandfound.common.Constants.Companion.LOST_ITEM_TITLE_KEY
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

    @get:PropertyName(LOST_ITEM_LOCATION_KEY)
    @set:PropertyName(LOST_ITEM_LOCATION_KEY)
    var location: String? = null,
)