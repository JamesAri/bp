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
    val route: String,
    val icon: NavIcon,
    var badgeCount: Int = 0,
) {

    object Home : NavItem(
        route = Screen.FindLostItemScreen.route,
        icon = NavIcon(Icons.Filled.Home),
    )

    object FindItem : NavItem(
        route = Screen.FindLostItemScreen.route,
        icon = NavIcon(Icons.Filled.Search),
    )

    object MyPosts : NavItem(
        route = Screen.MyPostsScreen.route,
        icon = NavIcon(res = R.drawable.ic_post_add),
    )

    object Settings : NavItem(
        route = Screen.SettingsScreen.route,
        icon = NavIcon(Icons.Filled.Settings),
    )

    object Profile : NavItem(
        route = Screen.ProfileScreen.route,
        icon = NavIcon(Icons.Filled.AccountCircle),
    )

    object AboutApp : NavItem(
        route = Screen.AboutAppScreen.route,
        icon = NavIcon(Icons.Filled.Info),
    )

    object Maps : NavItem(
        route = Screen.LostItemsMapScreen.route,
        icon = NavIcon(res = R.drawable.ic_map_with_marker),
    )

    object More : NavItem(
        route = "",
        icon = NavIcon(res = R.drawable.ic_more_horiz),
    )
}



