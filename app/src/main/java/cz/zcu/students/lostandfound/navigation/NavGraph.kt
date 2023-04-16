package cz.zcu.students.lostandfound.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.FloatType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.zcu.students.lostandfound.common.constants.Navigation.Companion.LATITUDE
import cz.zcu.students.lostandfound.common.constants.Navigation.Companion.LONGITUDE
import cz.zcu.students.lostandfound.common.constants.Navigation.Companion.LOST_ITEM_ID
import cz.zcu.students.lostandfound.common.constants.Navigation.Companion.NAVIGATION_LOCATION_KEY
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthScreen
import cz.zcu.students.lostandfound.common.features.map.domain.location_coordinates.LocationCoordinates
import cz.zcu.students.lostandfound.features.about_app.presentation.AboutAppScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item.AddLostItemScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.FindLostItemScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.lost_item_detail.LostItemDetailScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.lost_items_map.LostItemsMap
import cz.zcu.students.lostandfound.features.lost_items.presentation.mark_lost_item.MarkLostItemScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.my_posts.MyPostsScreen
import cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item.UpdatePostScreen
import cz.zcu.students.lostandfound.features.profile.presentation.change_phone_number.EditPhoneNumberScreen
import cz.zcu.students.lostandfound.features.profile.presentation.profile_view.ProfileScreen
import cz.zcu.students.lostandfound.features.settings.presentation.settings.SettingsScreen
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
                },
                navigateToMapMarker = { latitude, longitude ->
                    val route = Screen.LostItemsMapScreen.route +
                            "?$LATITUDE=$latitude" +
                            "&$LONGITUDE=$longitude"
                    Log.d("navigateToMapMarker", "NavGraph: $latitude $longitude $route")

                    navController.navigate(route)
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
                navigateToMapMarker = { latitude, longitude ->
                    val route = Screen.LostItemsMapScreen.route +
                            "?$LATITUDE=$latitude" +
                            "&$LONGITUDE=$longitude"
                    navController.navigate(route)
                }
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
                navigateToMarkLostItemScreen = {
                    navController.navigate(Screen.MarkLostItemScreen.route)
                },
                navController = navController,
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
            route = "${Screen.LostItemsMapScreen.route}?$LATITUDE={$LATITUDE}&$LONGITUDE={$LONGITUDE}",
            arguments = listOf(
                navArgument(LATITUDE) {
                    nullable = true
                    defaultValue = null
                    type = StringType
                },
                navArgument(LONGITUDE) {
                    nullable = true
                    defaultValue = null
                    type = StringType
                }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString(LATITUDE)?.toDouble()
            val longitude = backStackEntry.arguments?.getString(LONGITUDE)?.toDouble()
            var location: LocationCoordinates? = null
            if (latitude != null && longitude != null) {
                location = LocationCoordinates(latitude, longitude)
            }
            LostItemsMap(location = location)
        }
        composable(
            route = Screen.MarkLostItemScreen.route
        ) {
            MarkLostItemScreen(navigateBack = { location ->
                val lostItemLocation =
                    if (location == null) null else listOf(location.latitude, location.longitude)
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NAVIGATION_LOCATION_KEY, lostItemLocation)
                navController.popBackStack()
            })
        }
    }
}

