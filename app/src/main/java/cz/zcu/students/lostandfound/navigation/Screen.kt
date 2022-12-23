package cz.zcu.students.lostandfound.navigation

import cz.zcu.students.lostandfound.core.Constants.Companion.POSTS_SCREEN
import cz.zcu.students.lostandfound.core.Constants.Companion.UPDATE_POST_SCREEN

sealed class Screen(val route: String) {
    object PostsScreen: Screen(POSTS_SCREEN)
    object UpdatePostScreen: Screen(UPDATE_POST_SCREEN)
}
