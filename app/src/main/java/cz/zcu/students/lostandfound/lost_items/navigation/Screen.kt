package cz.zcu.students.lostandfound.lost_items.navigation

import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.POSTS_SCREEN
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.UPDATE_POST_SCREEN

sealed class Screen(val route: String) {
    object LostItemsScreen: Screen(POSTS_SCREEN)
    object UpdateLostItemScreen: Screen(UPDATE_POST_SCREEN)
}
