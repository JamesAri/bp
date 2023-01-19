package cz.zcu.students.lostandfound.navigation.navgraph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_ID
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item.AddLostItemScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.LostItemsScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item.UpdatePostScreen
import cz.zcu.students.lostandfound.features.profile.presentation.edit_phone_number.EditPhoneNumberScreen
import cz.zcu.students.lostandfound.features.profile.presentation.preview.ProfileScreen
import cz.zcu.students.lostandfound.features.settings.presentation.settings.SettingsScreen
import cz.zcu.students.lostandfound.navigation.Screen
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    coroutineScope: CoroutineScope,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(
            route = Screen.AuthScreen.route
        ) {
            AuthScreen(navigateToMainScreen = {
                navController.navigate(startDestination)
            })
        }
        composable(
            route = Screen.FindItemScreen.route
        ) {
            LostItemsScreen(
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
            route = Screen.AddLostItemScreen.route
        ) {
            AddLostItemScreen()
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
            AddLostItemScreen() // todo
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
            SettingsScreen()
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(
                navigateToLoginScreen = {
                    navController.navigate(Screen.AuthScreen.route)
                },
                navigateToEditPhoneNumber = {
                    navController.navigate(Screen.EditPhoneNumber.route)
                }
            )
        }
        composable(
            route = Screen.EditPhoneNumber.route
        ) {
            EditPhoneNumberScreen(
                coroutineScope = coroutineScope,
                navigateToProfile = {
                    navController.navigate(Screen.ProfileScreen.route)
                },
            )
        }
        composable(
            route = Screen.AboutApp.route
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(Screen.AboutApp.route)
            }
        }

    }
}