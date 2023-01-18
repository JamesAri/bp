package cz.zcu.students.lostandfound.common.auth.data.remote.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import cz.zcu.students.lostandfound.common.Constants

data class DbUserDto(
    @get:DocumentId
    val id: String,

    @get:PropertyName(Constants.USER_PHONE_NUMBER)
    @set:PropertyName(Constants.USER_PHONE_NUMBER)
    var phoneNumber: String? = null,

    @get:PropertyName(Constants.USER_CREATED_AT_KEY)
    @set:PropertyName(Constants.USER_CREATED_AT_KEY)
    @ServerTimestamp
    var createdAt: Timestamp? = null,
)

