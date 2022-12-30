package cz.zcu.students.lostandfound.navigation

import cz.zcu.students.lostandfound.common.Constants.Companion.AUTH_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.FAVORITES_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.FIND_ITEM_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.HELP_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.INBOX_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.MY_POSTS_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.NOTIFICATIONS_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.PROFILE_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.SETTINGS_SCREEN
import cz.zcu.students.lostandfound.common.Constants.Companion.UPDATE_LOST_ITEM_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen : Screen(AUTH_SCREEN)
    object FindItemScreen : Screen(FIND_ITEM_SCREEN)
    object UpdateLostItemScreen : Screen(UPDATE_LOST_ITEM_SCREEN)
    object InboxScreen : Screen(INBOX_SCREEN)
    object MyPostsScreen : Screen(MY_POSTS_SCREEN)
    object FavoritesScreen : Screen(FAVORITES_SCREEN)
    object NotificationsScreen : Screen(NOTIFICATIONS_SCREEN)
    object SettingsScreen : Screen(SETTINGS_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
    object HelpScreen : Screen(HELP_SCREEN)
}

val MappedRouteNames = mapOf(
    Screen.AuthScreen.route to "Authentication",
    Screen.FindItemScreen.route to "Find Item",
    Screen.UpdateLostItemScreen.route to "Update Lost Item",
    Screen.InboxScreen.route to "Inbox",
    Screen.MyPostsScreen.route to "Posts",
    Screen.FavoritesScreen.route to "Favorites",
    Screen.NotificationsScreen.route to "Notifications",
    Screen.SettingsScreen.route to "Settings",
    Screen.ProfileScreen.route to "Profile",
    Screen.HelpScreen.route to "Help & feedback",
).withDefault {
    it
}
