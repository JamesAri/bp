package cz.zcu.students.lostandfound.common.features.auth.data.mappers

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.CurrentUserDto
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.DbUserDto
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User

/**
 * Maps DTO user [DbUserDto] to the domain [User] object.
 *
 * @return domain [User] object.
 */
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

/**
 * Maps currently logged DTO user [CurrentUserDto] to the domain [User]
 * object.
 *
 * @return domain [User] object.
 */
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

/**
 * Maps domain [User] object to the DTO [DbUserDto].
 *
 * @return DTO object [DbUserDto].
 */
fun User.toDbUserDto(): DbUserDto {
    return DbUserDto(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        email = email,
        photoUri = photoUri.toString(),
    )
}

/**
 * Maps user [FirebaseUser] to DTO user [DbUserDto].
 *
 * @return DTO [DbUserDto].
 */
fun FirebaseUser.toDbUserDto(): DbUserDto {
    email ?: throw Exception("auth: missing email reference")
    displayName ?: throw Exception("auth: missing name reference")

    return DbUserDto(
        id = uid,
        name = displayName,
        email = email,
    )
}

