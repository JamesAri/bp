package cz.zcu.students.lostandfound.features.lost_items.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_CREATED_AT_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_DESCRIPTION_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_IS_FOUND_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_LOCATION_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_OWNER_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_TITLE_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.LOST_ITEM_URI_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.POST_OWNER_KEY
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

    @get:PropertyName(LOST_ITEM_IS_FOUND_KEY)
    @set:PropertyName(LOST_ITEM_IS_FOUND_KEY)
    var isFound: Boolean? = null,

    @get:PropertyName(LOST_ITEM_CREATED_AT_KEY)
    @set:PropertyName(LOST_ITEM_CREATED_AT_KEY)
    @ServerTimestamp
    var createdAt: Timestamp? = null,

    @get:PropertyName(LOST_ITEM_URI_KEY)
    @set:PropertyName(LOST_ITEM_URI_KEY)
    var imageUri: String? = null,

    @get:PropertyName(LOST_ITEM_LOCATION_KEY)
    @set:PropertyName(LOST_ITEM_LOCATION_KEY)
    var location: String? = null,

    @get:PropertyName(POST_OWNER_KEY)
    @set:PropertyName(POST_OWNER_KEY)
    var postOwnerId: String? = null,

    @get:PropertyName(LOST_ITEM_OWNER_KEY)
    @set:PropertyName(LOST_ITEM_OWNER_KEY)
    var itemOwnerId: String? = null,

    )