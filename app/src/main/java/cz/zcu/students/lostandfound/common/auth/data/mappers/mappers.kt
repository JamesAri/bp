package cz.zcu.students.lostandfound.common.auth.data.mappers

import cz.zcu.students.lostandfound.common.auth.data.remote.dto.DbUserDto
import cz.zcu.students.lostandfound.common.auth.data.remote.dto.UserDto
import cz.zcu.students.lostandfound.common.auth.domain.user.User
import cz.zcu.students.lostandfound.common.extensions.isNull

fun UserDto.toUser(): User {
    val email = authUser.email ?: throw Exception("missing email reference")
    val name = authUser.displayName ?: throw Exception("missing name reference")
    if (dbUser != null && authUser.uid != dbUser.id) throw Exception("inconsistent user id mapping")

    return User(
        id = authUser.uid,
        email = email,
        name = name,
        phoneNumber = dbUser?.phoneNumber,
        photoUri = authUser.photoUrl
    )
}

fun User.toDbUserDto(): DbUserDto {
    return DbUserDto(
        id = id,
        phoneNumber = phoneNumber,
    )
}