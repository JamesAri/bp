package cz.zcu.students.lostandfound.navigation

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.zcu.students.lostandfound.common.auth.presentation.login.AuthScreen
import cz.zcu.students.lostandfound.common.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.common.extensions.isNull
import cz.zcu.students.lostandfound.navigation.app_bars.NavigationAppBars
import cz.zcu.students.lostandfound.navigation.drawer.NavigationDrawer
import cz.zcu.students.lostandfound.navigation.navgraph.NavGraph

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
                        startDestination =  Screen.ProfileScreen.route,
                    )
                }
            )
        }
    )
}