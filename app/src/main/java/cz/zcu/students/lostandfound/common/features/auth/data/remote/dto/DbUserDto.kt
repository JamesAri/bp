package cz.zcu.students.lostandfound.common.features.auth.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.USER_CREATED_AT_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.USER_EMAIL_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.USER_NAME_KEY
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.USER_PHONE_NUMBER
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.USER_PHOTO_URI_KEY

/**
 * Firestore DTO user.
 *
 * @property id **id** field.
 * @property email **email** field.
 * @property name **name** field.
 * @property photoUri *photo_uri* field.
 * @property phoneNumber **phone_number** field.
 * @property createdAt **created_at** field.
 */
data class DbUserDto(
    // Firestore dto object must have default constructor, thus empty string.
    // The id will get assigned based on currently cached fire-auth user.
    @get:DocumentId
    val id: String = "",

    @get:PropertyName(USER_EMAIL_KEY)
    @set:PropertyName(USER_EMAIL_KEY)
    var email: String? = null,

    @get:PropertyName(USER_NAME_KEY)
    @set:PropertyName(USER_NAME_KEY)
    var name: String? = null,

    @get:PropertyName(USER_PHOTO_URI_KEY)
    @set:PropertyName(USER_PHOTO_URI_KEY)
    var photoUri: String? = null,

    @get:PropertyName(USER_PHONE_NUMBER)
    @set:PropertyName(USER_PHONE_NUMBER)
    var phoneNumber: String? = null,

    @get:PropertyName(USER_CREATED_AT_KEY)
    @set:PropertyName(USER_CREATED_AT_KEY)
    @ServerTimestamp
    var createdAt: Timestamp? = null,


    )

