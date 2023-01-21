package cz.zcu.students.lostandfound.common.features.auth.data.remote.dto

import com.google.firebase.auth.FirebaseUser

data class CurrentUserDto(
    val authUser: FirebaseUser,
    val dbUser: DbUserDto,
)
