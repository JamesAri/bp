package cz.zcu.students.lostandfound.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import cz.zcu.students.lostandfound.R

class NavIcon(
    val vector: ImageVector? = null,
    @DrawableRes val res: Int? = null,
) {
    @Composable
    fun getIcon(): ImageVector {
        if (vector != null)
            return vector
        if (res != null)
            return ImageVector.vectorResource(id = res)
        throw IllegalArgumentException("Must pass either vector or resource id")
    }
}

sealed class NavItem(
    val name: String,
    val route: String,
    val icon: NavIcon,
    var badgeCount: Int = 0,
) {

    object Home : NavItem(
        name = MappedRouteNames.getValue(Screen.FindLostItemScreen.route),
        route = Screen.FindLostItemScreen.route,
        icon = NavIcon(Icons.Filled.Home),
    )

    object Inbox : NavItem(
        name = MappedRouteNames.getValue(Screen.InboxScreen.route),
        route = Screen.InboxScreen.route,
        icon = NavIcon(res = R.drawable.ic_inbox),
    )

    object FindItem : NavItem(
        name = MappedRouteNames.getValue(Screen.FindLostItemScreen.route),
        route = Screen.FindLostItemScreen.route,
        icon = NavIcon(Icons.Filled.Search),
    )

    object MyPosts : NavItem(
        name = MappedRouteNames.getValue(Screen.MyPostsScreen.route),
        route = Screen.MyPostsScreen.route,
        icon = NavIcon(res = R.drawable.ic_post_add),
    )

    object Favorites : NavItem(
        name = MappedRouteNames.getValue(Screen.FavoritesScreen.route),
        route = Screen.FavoritesScreen.route,
        icon = NavIcon(Icons.Filled.Favorite),
    )

    object Notifications : NavItem(
        name = MappedRouteNames.getValue(Screen.NotificationsScreen.route),
        route = Screen.NotificationsScreen.route,
        icon = NavIcon(Icons.Filled.Notifications),
    )

    object Settings : NavItem(
        name = MappedRouteNames.getValue(Screen.SettingsScreen.route),
        route = Screen.SettingsScreen.route,
        icon = NavIcon(Icons.Filled.Settings),
    )

    object Profile : NavItem(
        name = MappedRouteNames.getValue(Screen.ProfileScreen.route),
        route = Screen.ProfileScreen.route,
        icon = NavIcon(Icons.Filled.AccountCircle),
    )

    object AboutApp : NavItem(
        name = MappedRouteNames.getValue(Screen.AboutAppScreen.route),
        route = Screen.AboutAppScreen.route,
        icon = NavIcon(Icons.Filled.Info),
    )

    object Maps : NavItem(
        name =  MappedRouteNames.getValue(Screen.MapScreen.route),
        route = Screen.MapScreen.route,
        icon = NavIcon(res = R.drawable.ic_map_with_marker),
    )

    object More : NavItem(
        name = "",
        route = "",
        icon = NavIcon(res = R.drawable.ic_more_horiz),
    )
}



