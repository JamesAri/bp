package cz.zcu.students.lostandfound.common.features.auth.data.remote.dto

import com.google.firebase.auth.FirebaseUser

/**
 * Current user as DTO.
 *
 * @property authUser class [FirebaseUser] representing Firebase Auth user.
 * @property dbUser DTO [DbUserDto] for Firestore database.
 */
data class CurrentUserDto(
    val authUser: FirebaseUser,
    val dbUser: DbUserDto,
)
