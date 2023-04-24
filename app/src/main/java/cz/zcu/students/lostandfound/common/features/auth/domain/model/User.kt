package cz.zcu.students.lostandfound.common.features.auth.domain.model

import android.net.Uri

/**
 * Domain layer user representation.
 *
 * @property id user's id.
 * @property email user's email.
 * @property name user's name.
 * @property photoUri user's photo uri.
 * @property phoneNumber user's phone number.
 */
data class User(
    val id: String,
    val email: String,
    val name: String,
    val photoUri: Uri,
    val phoneNumber: String?,
)
