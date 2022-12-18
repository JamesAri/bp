package cz.zcu.students.jetpackcomposetestproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

//data class BottomNavItem(
//    val name: String,
//    val route: String,
//    val icon: ImageVector,
//    val badgeCount: Int = 0
//)

sealed class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    var badgeCount: Int = 0
) {
    object Home : BottomNavItem(
        name = "Home",
        route = "home",
        icon = Icons.Default.Home
    )

    object Notifications : BottomNavItem(
        name = "Notifications",
        route = "notifications",
        icon = Icons.Default.Notifications
    )

    object Settings : BottomNavItem(
        name = "Settings",
        route = "settings",
        icon = Icons.Default.Settings
    )
}

val BottomNavItems: List<BottomNavItem> = listOf(
    BottomNavItem.Home,
    BottomNavItem.Notifications,
    BottomNavItem.Settings,
)