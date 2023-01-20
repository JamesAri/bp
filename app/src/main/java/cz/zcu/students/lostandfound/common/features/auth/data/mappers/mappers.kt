package cz.zcu.students.lostandfound.common.features.auth.data.mappers

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.DbUserDto
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.UserDto
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User

fun UserDto.toUser(): User {
    if (dbUser == null) throw Exception("missing user db reference")
    authUser.email ?: throw Exception("auth: missing email reference")
    dbUser.email ?: throw Exception("db: missing email reference")
    authUser.displayName ?: throw Exception("auth: missing name reference")
    dbUser.name ?: throw Exception("db: missing name reference")
    dbUser.photoUri ?: throw Exception("db: missing profile picture reference")

    if (authUser.uid != dbUser.id) throw Exception("inconsistent user id mapping")
    if (authUser.displayName != dbUser.name) throw Exception("inconsistent user name mapping")
    if (authUser.email != dbUser.email) throw Exception("inconsistent user email mapping")

    return User(
        id = dbUser.id,
        email = dbUser.email!!,
        name = dbUser.name!!,
        phoneNumber = dbUser.phoneNumber,
        photoUri = Uri.parse(dbUser.photoUri!!),
    )
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
        photoUri = photoUrl?.toString(),
    )
}