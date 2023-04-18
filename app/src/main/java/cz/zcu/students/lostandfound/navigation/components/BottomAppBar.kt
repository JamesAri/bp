package cz.zcu.students.lostandfound.navigation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.zcu.students.lostandfound.navigation.NavItem

@Composable
fun BottomAppBar(
    navController: NavHostController,
    mappedRouteNamesState: Map<String, String>,
) {
    val backStackEntryState = navController.currentBackStackEntryAsState()
    val items = listOf(
        NavItem.FindItem,
        NavItem.MyPosts,
        NavItem.Maps,
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        items.forEach { item ->

            val selected = item.route == backStackEntryState.value?.destination?.route
            val name = mappedRouteNamesState.getValue(item.route)

            NavigationBarItem(
                label = { Text(name) },
                selected = selected,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = MaterialTheme.colorScheme.onTertiary,
                                        content = {
                                            Text(text = item.badgeCount.toString())
                                        })
                                },
                                content = {
                                    Icon(
                                        item.icon.getIcon(),
                                        contentDescription = name
                                    )
                                }
                            )
                        } else {
                            Icon(
                                item.icon.getIcon(),
                                contentDescription = name
                            )
                        }
                    }
                },
            )
        }
    }
}