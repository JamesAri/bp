package cz.zcu.students.lostandfound.common.auth.domain.user

import android.net.Uri

data class User(
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String?,
    val photoUri: Uri?,
)
