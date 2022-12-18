package cz.zcu.students.jetpackcomposetestproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MyBottomNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("notifications") {
            NotificationsScreen()
        }
        composable("settings") {
            SettingsScreen()
        }
    }
}

// [!] In real app there would be no components - create separated file for that

// Bottom navbar component
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntryState = navController.currentBackStackEntryAsState()
    NavigationBar(
    ) {
        items.forEach { item ->
//            val selected = item.route == navController.currentDestination?.route // wont work, not a state
            val selected = item.route == backStackEntryState.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.secondary,
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        content = {
                                            Text(text = item.badgeCount.toString())
                                        })
                                },
                                content = {
                                    Icon(
                                        item.icon,
                                        contentDescription = item.name
                                    )
                                }
                            )
                        } else {
                            Icon(
                                item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}


// Demo Screens
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen Placeholder")
    }
}

@Composable
fun NotificationsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Notifications Screen Placeholder")
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings Screen Placeholder")
    }
}