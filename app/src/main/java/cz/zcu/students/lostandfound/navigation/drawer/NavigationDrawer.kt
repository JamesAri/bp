package cz.zcu.students.lostandfound.navigation.drawer

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


@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val group1 = listOf(
        NavItem.Home,
        NavItem.Inbox,
    )
    val group2 = listOf(
        NavItem.Settings,
        NavItem.Help,
    )

    val backStackEntryState = navController.currentBackStackEntryAsState()
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary,
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
                    backStackEntryState = backStackEntryState
                )
                Divider()
                Spacer(Modifier.height(MaterialTheme.spacing.small))
                IterateGroup(
                    items = group2,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    backStackEntryState = backStackEntryState
                )
            }
        },
        content = content
    )
}

@Composable
fun IterateGroup(
    items: List<NavItem>,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    backStackEntryState: State<NavBackStackEntry?>,
) {
    items.forEach { item ->
        val selected = item.route == backStackEntryState.value?.destination?.route
        NavigationDrawerItem(
            icon = {
                Icon(
                    item.icon.getIcon(),
                    contentDescription = item.name
                )
            },
            label = {
                Text(item.name)
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
