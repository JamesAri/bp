package cz.zcu.students.lostandfound.navigation

import cz.zcu.students.lostandfound.lost_items.shared.Constants.Companion.AUTH_SCREEN
import cz.zcu.students.lostandfound.lost_items.shared.Constants.Companion.LOST_ITEMS_SCREEN
import cz.zcu.students.lostandfound.lost_items.shared.Constants.Companion.UPDATE_LOST_ITEM_SCREEN

sealed class Screen(val route: String) {
    object AuthScreen: Screen(AUTH_SCREEN)
    object LostItemsScreen: Screen(LOST_ITEMS_SCREEN)
    object UpdateLostItemScreen: Screen(UPDATE_LOST_ITEM_SCREEN)
}
