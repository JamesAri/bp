package cz.zcu.students.lostandfound.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.zcu.students.lostandfound.navigation.app_bars.BottomAppBar
import cz.zcu.students.lostandfound.navigation.app_bars.TopAppBar
import cz.zcu.students.lostandfound.navigation.drawer.NavigationDrawer
import cz.zcu.students.lostandfound.navigation.navgraph.NavGraph
import kotlinx.coroutines.CoroutineScope

val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf { error("No SnackbarHostState provided") }


@Composable
fun App() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val navController: NavHostController = rememberNavController()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        NavigationDrawer(
            drawerState = drawerState,
            coroutineScope = coroutineScope,
            navController = navController,
            content = {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopAppBar(
                            drawerState = drawerState,
                            coroutineScope = coroutineScope,
                            navController = navController,
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            navController = navController
                        )
                    },
                    content = { paddingValues ->
                        NavGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            navController = navController,
                            startDestination = Screen.ProfileScreen.route,
                            coroutineScope = coroutineScope,
                        )
                    }
                )
            }
        )
    }
}
