package cz.zcu.students.lostandfound.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.zcu.students.lostandfound.features.settings.presentation.settings.SettingsViewModel
import cz.zcu.students.lostandfound.navigation.components.BottomAppBar
import cz.zcu.students.lostandfound.navigation.components.NavigationDrawer
import cz.zcu.students.lostandfound.navigation.components.TopAppBar
import kotlinx.coroutines.CoroutineScope

val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> =
    compositionLocalOf { error("No SnackbarHostState provided") }


@Composable
fun App(
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val navController: NavHostController = rememberNavController()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var mappedRouteNamesState by remember {
        mutableStateOf(mappedRouteNamesFactory(context = context))
    }

    LaunchedEffect(appSettingsViewModel.languageState) {
        mappedRouteNamesState = mappedRouteNamesFactory(context = context)
    }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        NavigationDrawer(
            drawerState = drawerState,
            coroutineScope = coroutineScope,
            navController = navController,
            gesturesEnabled = drawerState.isOpen,
            mappedRouteNamesState = mappedRouteNamesState,
            content = {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopAppBar(
                            drawerState = drawerState,
                            coroutineScope = coroutineScope,
                            navController = navController,
                            mappedRouteNamesState = mappedRouteNamesState,
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            navController = navController,
                            mappedRouteNamesState = mappedRouteNamesState,
                        )
                    },
                    content = { paddingValues ->
                        NavGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            navController = navController,
                            startDestination = Screen.FindLostItemScreen.route,
                            coroutineScope = coroutineScope,
                        )
                    }
                )
            }
        )
    }
}

