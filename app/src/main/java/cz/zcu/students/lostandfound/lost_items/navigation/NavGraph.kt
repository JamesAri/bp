package cz.zcu.students.lostandfound.lost_items.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.lost_items.core.Constants.Companion.POST_ID
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
            route = "${Screen.UpdateLostItemScreen.route}/{$POST_ID}",
            arguments = listOf(
                navArgument(POST_ID) {
                    type = IntType
                }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt(POST_ID) ?: 0
            UpdatePostScreen(
                postId = postId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}