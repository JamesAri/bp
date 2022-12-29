package cz.zcu.students.lostandfound.auth.data.mappers

import com.google.firebase.auth.FirebaseUser
import cz.zcu.students.lostandfound.auth.domain.user.User
import cz.zcu.students.lostandfound.lost_items.shared.Constants.Companion.NO_VALUE

fun FirebaseUser.toUser(): User? {
//    if (email == null)
//        return null
//
//    if (displayName == null)
//        return null
    // todo
    return User(
        id = uid,
        email = email ?: NO_VALUE,
        name = displayName ?: NO_VALUE,
        phoneNumber = phoneNumber,
        photoUri = photoUrl
    )
}