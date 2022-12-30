package cz.zcu.students.lostandfound.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.auth.presentation.login.AuthScreen
import cz.zcu.students.lostandfound.lost_items.presentation.lost_items.PostsScreen
import cz.zcu.students.lostandfound.lost_items.presentation.update_lost_item.UpdatePostScreen
import cz.zcu.students.lostandfound.common.Constants.Companion.LOST_ITEM_ID

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.FindItemScreen.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.AuthScreen.route
        ) {
            AuthScreen(navigateToMainScreen = {
                navController.navigate(Screen.FindItemScreen.route)
            })
        }
        composable(
            route = Screen.FindItemScreen.route
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
        composable(
            route = Screen.InboxScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
               Text(Screen.InboxScreen.route)
            }
        }
        composable(
            route = Screen.MyPostsScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.MyPostsScreen.route)
            }
        }
        composable(
            route = Screen.FavoritesScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.FavoritesScreen.route)
            }
        }
        composable(
            route = Screen.NotificationsScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.NotificationsScreen.route)
            }
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.SettingsScreen.route)
            }
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.ProfileScreen.route)
            }
        }
        composable(
            route = Screen.HelpScreen.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.HelpScreen.route)
            }
        }

    }
}