package cz.zcu.students.lostandfound.common.features.auth.data.mappers

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.DbUserDto
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.CurrentUserDto
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User

fun DbUserDto.toUser(): User {
    email ?: throw Exception("db: missing email reference")
    name ?: throw Exception("db: missing name reference")
    photoUri ?: throw Exception("db: missing profile picture reference")

    return User(
        id = id,
        email = email!!,
        name = name!!,
        phoneNumber = phoneNumber,
        photoUri = Uri.parse(photoUri!!),
    )
}

fun CurrentUserDto.toUser(): User {
    authUser.email ?: throw Exception("auth: missing email reference")
    authUser.displayName ?: throw Exception("auth: missing name reference")

    val currentUser = dbUser.toUser()

    // consistency check
    if (authUser.uid != currentUser.id) throw Exception("inconsistent user id mapping")
    if (authUser.displayName != currentUser.name) throw Exception("inconsistent user name mapping")
    if (authUser.email != currentUser.email) throw Exception("inconsistent user email mapping")

    return currentUser
}

fun User.toDbUserDto(): DbUserDto {
    return DbUserDto(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        email = email,
        photoUri = photoUri.toString(),
    )
}

fun FirebaseUser.toDbUserDto() : DbUserDto {
    email ?: throw Exception("auth: missing email reference")
    displayName ?: throw Exception("auth: missing name reference")

    return DbUserDto(
        id = uid,
        name = displayName,
        email = email,
    )
}

