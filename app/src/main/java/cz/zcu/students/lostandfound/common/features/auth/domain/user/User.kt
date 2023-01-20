package cz.zcu.students.lostandfound.common.features.auth.domain.user

import android.net.Uri

data class User(
    val id: String,
    val email: String,
    val name: String,
    val photoUri: Uri,
    val phoneNumber: String?,
)
