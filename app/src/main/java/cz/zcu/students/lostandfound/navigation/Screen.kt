package cz.zcu.students.lostandfound.navigation

import cz.zcu.students.lostandfound.common.constants.General.Companion.ADD_LOST_ITEM_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.AUTH_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.FAVORITES_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.FIND_ITEM_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.ABOUT_APP_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.CHANGE_PHONE_NUMBER_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.CHANGE_PROFILE_PICTURE_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.INBOX_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.MY_POSTS_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.NOTIFICATIONS_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.PROFILE_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.SETTINGS_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.UPDATE_LOST_ITEM_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen : Screen(AUTH_SCREEN)
    object FindItemScreen : Screen(FIND_ITEM_SCREEN)
    object UpdateLostItemScreen : Screen(UPDATE_LOST_ITEM_SCREEN)
    object AddLostItemScreen : Screen(ADD_LOST_ITEM_SCREEN)
    object InboxScreen : Screen(INBOX_SCREEN)
    object MyPostsScreen : Screen(MY_POSTS_SCREEN)
    object FavoritesScreen : Screen(FAVORITES_SCREEN)
    object NotificationsScreen : Screen(NOTIFICATIONS_SCREEN)
    object SettingsScreen : Screen(SETTINGS_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
    object ChangePhoneNumberScreen : Screen(CHANGE_PHONE_NUMBER_SCREEN)
    object ChangeProfilePictureScreen : Screen(CHANGE_PROFILE_PICTURE_SCREEN)
    object AboutAppScreen : Screen(ABOUT_APP_SCREEN)
}

val MappedRouteNames = mapOf(
    Screen.AuthScreen.route to "Authentication",
    Screen.FindItemScreen.route to "Find item",
    Screen.UpdateLostItemScreen.route to "Update lost item",
    Screen.AddLostItemScreen.route to "Add lost item",
    Screen.InboxScreen.route to "Inbox",
    Screen.MyPostsScreen.route to "My posts",
    Screen.FavoritesScreen.route to "Favorites",
    Screen.NotificationsScreen.route to "Notifications",
    Screen.SettingsScreen.route to "Settings",
    Screen.ProfileScreen.route to "Profile",
    Screen.ChangePhoneNumberScreen.route to "Change phone number",
    Screen.ChangeProfilePictureScreen.route to "Change profile picture",
    Screen.AboutAppScreen.route to "About app",
).withDefault {
    it
}
