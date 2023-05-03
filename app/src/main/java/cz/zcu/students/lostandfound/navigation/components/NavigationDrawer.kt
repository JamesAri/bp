package cz.zcu.students.lostandfound.navigation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.navigation.NavItem
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * App's navigation drawer component.
 *
 * @param drawerState drawer state, i.e. closed/open.
 * @param coroutineScope coroutine scope to run blocking code.
 * @param navController app's navigation controller.
 * @param gesturesEnabled state to determine if gestures are enabled.
 * @param mappedRouteNamesState mapped screen routes to screen names.
 * @param content content inside of this component.
 */
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    gesturesEnabled: Boolean,
    mappedRouteNamesState: Map<String, String>,
    content: @Composable () -> Unit
) {
    val group1 = listOf(
        NavItem.Home,
        NavItem.MyPosts,
    )
    val group2 = listOf(
        NavItem.Settings,
        NavItem.AboutApp,
    )

    val backStackEntryState = navController.currentBackStackEntryAsState()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 29.dp)
                        .height(50.0.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.branding),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Divider()
                Spacer(Modifier.height(MaterialTheme.spacing.small))
                IterateGroup(
                    items = group1,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    backStackEntryState = backStackEntryState,
                    mappedRouteNamesState = mappedRouteNamesState,
                )
                Divider()
                Spacer(Modifier.height(MaterialTheme.spacing.small))
                IterateGroup(
                    items = group2,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    backStackEntryState = backStackEntryState,
                    mappedRouteNamesState = mappedRouteNamesState,
                )
            }
        },
        content = content,
    )
}

/**
 * Creates group of navigation elements.
 *
 * @param items [NavItem] items to create in group list.
 * @param navController app's navigation controller.
 * @param coroutineScope coroutine scope to run blocking code.
 * @param drawerState drawer state, i.e. closed/open.
 * @param mappedRouteNamesState mapped screen routes to screen names.
 * @param backStackEntryState navigation's back stack entry for retrieving
 *     route parameters.
 */
@Composable
private fun IterateGroup(
    items: List<NavItem>,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    mappedRouteNamesState: Map<String, String>,
    backStackEntryState: State<NavBackStackEntry?>,
) {
    items.forEach { item ->
        val selected = item.route == backStackEntryState.value?.destination?.route
        val name = mappedRouteNamesState.getValue(item.route)

        NavigationDrawerItem(
            icon = {
                Icon(
                    item.icon.getIcon(),
                    contentDescription = name
                )
            },
            label = {
                Text(name)
            },
            selected = selected,
            onClick = {
                coroutineScope.launch { drawerState.close() }
                navController.navigate(item.route)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
    }

}
