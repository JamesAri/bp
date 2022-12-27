package cz.zcu.students.lostandfound.navigation

import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.AUTH_SCREEN
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.POSTS_SCREEN
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.UPDATE_POST_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen: Screen(AUTH_SCREEN)
    object LostItemsScreen: Screen(POSTS_SCREEN)
    object UpdateLostItemScreen: Screen(UPDATE_POST_SCREEN)
}
