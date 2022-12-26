package cz.zcu.students.lostandfound.lost_items.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.LOST_ITEM_ID
import cz.zcu.students.lostandfound.lost_items.presentation.lost_items.PostsScreen
import cz.zcu.students.lostandfound.lost_items.presentation.update_lost_item.UpdatePostScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LostItemsScreen.route
    ) {
        composable(
            route = Screen.LostItemsScreen.route
        ) {
            PostsScreen(
                navigateToUpdatePostScreen = { postId ->
                    navController.navigate("${Screen.UpdateLostItemScreen.route}/${postId}")
                }
            )
        }
        composable(
            route = "${Screen.UpdateLostItemScreen.route}/{$LOST_ITEM_ID}",
            arguments = listOf(
                navArgument(LOST_ITEM_ID) {
                    type = IntType
                }
            )
        ) { backStackEntry ->
            val lostItemId = backStackEntry.arguments?.getString(LOST_ITEM_ID) ?: ""
            UpdatePostScreen(
                lostItemId = lostItemId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}