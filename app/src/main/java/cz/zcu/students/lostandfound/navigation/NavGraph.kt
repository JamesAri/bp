package cz.zcu.students.lostandfound.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.core.Constants.Companion.POST_ID
import cz.zcu.students.lostandfound.presentation.posts.PostsScreen
import cz.zcu.students.lostandfound.presentation.update_post.UpdatePostScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PostsScreen.route
    ) {
        composable(
            route = Screen.PostsScreen.route
        ) {
            PostsScreen(
                navigateToUpdatePostScreen = { postId ->
                    navController.navigate("${Screen.UpdatePostScreen.route}/${postId}")
                }
            )
        }
        composable(
            route = "${Screen.UpdatePostScreen.route}/{$POST_ID}",
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