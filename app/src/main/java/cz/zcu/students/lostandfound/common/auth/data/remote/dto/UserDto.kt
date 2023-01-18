package cz.zcu.students.lostandfound.common.auth.data.remote.dto

import com.google.firebase.auth.FirebaseUser

data class UserDto(
    val authUser: FirebaseUser,
    val dbUser: DbUserDto?,
)
