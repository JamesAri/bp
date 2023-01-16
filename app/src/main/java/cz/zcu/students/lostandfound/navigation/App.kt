package cz.zcu.students.lostandfound.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.zcu.students.lostandfound.navigation.app_bar.NavigationAppBars
import cz.zcu.students.lostandfound.navigation.drawer.NavigationDrawer

@Composable
fun App(
    navController: NavHostController = rememberNavController(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    NavigationDrawer(
        drawerState = drawerState,
        coroutineScope = scope,
        navController = navController,
        content = {
            NavigationAppBars(
                drawerState = drawerState,
                coroutineScope = scope,
                navController = navController,
                content = {
                    NavGraph(
                        navController = navController,
                        startDestination = Screen.SettingsScreen.route,
                    )
                }
            )
        }
    )
}