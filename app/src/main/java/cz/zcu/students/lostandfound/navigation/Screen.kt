package cz.zcu.students.lostandfound.navigation

import android.content.Context
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.constants.General.Companion.ABOUT_APP_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.ADD_LOST_ITEM_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.AUTH_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.CHANGE_PHONE_NUMBER_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.CHANGE_PROFILE_PICTURE_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.FIND_ITEM_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_DETAIL_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.MAP_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.MARK_LOST_ITEM_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.MY_POSTS_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.PROFILE_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.SETTINGS_SCREEN
import cz.zcu.students.lostandfound.common.constants.General.Companion.UPDATE_LOST_ITEM_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen : Screen(AUTH_SCREEN)
    object FindLostItemScreen : Screen(FIND_ITEM_SCREEN)
    object UpdateLostItemScreen : Screen(UPDATE_LOST_ITEM_SCREEN)
    object AddLostItemScreen : Screen(ADD_LOST_ITEM_SCREEN)
    object LostItemDetailScreen : Screen(LOST_ITEM_DETAIL_SCREEN)
    object MyPostsScreen : Screen(MY_POSTS_SCREEN)
    object SettingsScreen : Screen(SETTINGS_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
    object ChangePhoneNumberScreen : Screen(CHANGE_PHONE_NUMBER_SCREEN)
    object ChangeProfilePictureScreen : Screen(CHANGE_PROFILE_PICTURE_SCREEN)
    object AboutAppScreen : Screen(ABOUT_APP_SCREEN)
    object LostItemsMapScreen : Screen(MAP_SCREEN)
    object MarkLostItemScreen : Screen(MARK_LOST_ITEM_SCREEN)
}

fun mappedRouteNamesFactory(context: Context): Map<String, String> {
    return mapOf(
        Screen.AuthScreen.route to context.getString(R.string.navigation_auth),
        Screen.FindLostItemScreen.route to context.getString(R.string.navigation_find_lost_item),
        Screen.UpdateLostItemScreen.route to context.getString(R.string.navigation_update_lost_item),
        Screen.AddLostItemScreen.route to context.getString(R.string.navigation_add_lost_item),
        Screen.LostItemDetailScreen.route to context.getString(R.string.navigation_lost_item_detail),
        Screen.MyPostsScreen.route to context.getString(R.string.navigation_my_posts),
        Screen.SettingsScreen.route to context.getString(R.string.navigation_settings),
        Screen.ProfileScreen.route to context.getString(R.string.navigation_profile),
        Screen.ChangePhoneNumberScreen.route to context.getString(R.string.navigation_change_number),
        Screen.ChangeProfilePictureScreen.route to context.getString(R.string.navigation_change_profile_picture),
        Screen.AboutAppScreen.route to context.getString(R.string.navigation_about_app),
        Screen.LostItemsMapScreen.route to context.getString(R.string.navigation_maps),
        Screen.MarkLostItemScreen.route to context.getString(R.string.navigation_mark_lost_item),
    ).withDefault {
        it
    }
}
