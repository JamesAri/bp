package cz.zcu.students.lostandfound.common.features.auth.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import cz.zcu.students.lostandfound.common.constants.General

data class DbUserDto(
    // Firestore dto object must have default constructor, thus empty string.
    // The id will get assigned based on currently cached fire-auth user.
    @get:DocumentId
    val id: String = "",

    @get:PropertyName(General.USER_PHONE_NUMBER)
    @set:PropertyName(General.USER_PHONE_NUMBER)
    var phoneNumber: String? = null,

    @get:PropertyName(General.USER_CREATED_AT_KEY)
    @set:PropertyName(General.USER_CREATED_AT_KEY)
    @ServerTimestamp
    var createdAt: Timestamp? = null,
)

