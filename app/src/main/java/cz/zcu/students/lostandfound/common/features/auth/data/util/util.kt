package cz.zcu.students.lostandfound.common.features.auth.data.util

import android.net.Uri
import java.util.concurrent.ThreadLocalRandom

val DEFAULT_PROFILE_AVATARS = arrayOf(
    "default_profile_avatar_1",
    "default_profile_avatar_2",
    "default_profile_avatar_3",
    "default_profile_avatar_4",
    "default_profile_avatar_5",
)

val DEFAULT_PROFILE_AVATAR_URIS = DEFAULT_PROFILE_AVATARS.map {
    Uri.parse("android.resource://cz.zcu.students.lostandfound/drawable/$it")
}

fun getRandomProfileAvatarUri(): Uri {
    val randomIndex = ThreadLocalRandom.current().nextInt(DEFAULT_PROFILE_AVATAR_URIS.size)
    return DEFAULT_PROFILE_AVATAR_URIS[randomIndex]
}