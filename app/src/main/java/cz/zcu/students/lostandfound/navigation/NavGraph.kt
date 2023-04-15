package cz.zcu.students.lostandfound.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.common.constants.General.Companion.LOST_ITEM_ID
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthScreen
import cz.zcu.students.lostandfound.features.about_app.presentation.AboutAppScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item.AddLostItemScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.FindLostItemScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.lost_item_detail.LostItemDetailScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.my_posts.MyPostsScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item.UpdatePostScreen
import cz.zcu.students.lostandfound.features.map.presentation.MapScreen
import cz.zcu.students.lostandfound.features.profile.presentation.change_phone_number.EditPhoneNumberScreen
import cz.zcu.students.lostandfound.features.profile.presentation.profile_view.ProfileScreen
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
            route = Screen.FindLostItemScreen.route
        ) {
            FindLostItemScreen(
                navigateToLostItemDetail = { id ->
                    navController.navigate("${Screen.LostItemDetailScreen.route}/${id}")
                }
            )
        }
        composable(
            route = "${Screen.LostItemDetailScreen.route}/{$LOST_ITEM_ID}",
            arguments = listOf(
                navArgument(LOST_ITEM_ID) {
                    type = StringType
                }
            )
        ) { backStackEntry ->
            val lostItemId = backStackEntry.arguments?.getString(LOST_ITEM_ID)
            LostItemDetailScreen(
                lostItemId = lostItemId,
            )
        }
        composable(
            route = "${Screen.UpdateLostItemScreen.route}/{$LOST_ITEM_ID}",
            arguments = listOf(
                navArgument(LOST_ITEM_ID) {
                    type = StringType
                }
            )
        ) { backStackEntry ->
            val lostItemId = backStackEntry.arguments?.getString(LOST_ITEM_ID)
            UpdatePostScreen(
                lostItemId = lostItemId,
                coroutineScope = coroutineScope,
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Screen.AddLostItemScreen.route
        ) {
            AddLostItemScreen(
                coroutineScope = coroutineScope,
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Screen.MyPostsScreen.route
        ) {
            MyPostsScreen(
                navigateToAddPosts = {
                    navController.navigate(Screen.AddLostItemScreen.route)
                },
                navigateToUpdateScreen = { id ->
                    navController.navigate("${Screen.UpdateLostItemScreen.route}/${id}")
                }
            )
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
                navigateToChangePhoneNumber = {
                    navController.navigate(Screen.ChangePhoneNumberScreen.route)
                }
            )
        }
        composable(
            route = Screen.ChangePhoneNumberScreen.route
        ) {
            EditPhoneNumberScreen(
                coroutineScope = coroutineScope,
                navigateToProfile = {
                    navController.navigate(Screen.ProfileScreen.route)
                },
            )
        }
        composable(
            route = Screen.AboutAppScreen.route
        ) {
            AboutAppScreen()
        }
        composable(
            route = Screen.AboutAppScreen.route
        ) {
            AboutAppScreen()
        }
        composable(
            route = Screen.MapScreen.route
        ) {
            MapScreen()
        }
    }
}

