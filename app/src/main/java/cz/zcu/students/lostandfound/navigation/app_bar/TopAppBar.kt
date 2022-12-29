package cz.zcu.students.lostandfound.navigation.app_bar

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.navigation.drawer.NavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Lost and Found",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed)
                                    drawerState.open()
                                else
                                    drawerState.close()
                            }
                        })
                    {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        bottomBar = {
            var selectedItem by remember { mutableStateOf(0) }
            val items = listOf("Find Item", "My Posts", "Favorites", "More")
            val icons = listOf(
                Icons.Filled.Search,
                Icons.Filled.Add,
                Icons.Filled.Favorite,
                ImageVector.vectorResource(id = R.drawable.ic_more_horiz)
            )
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
        content = { innerPadding ->
            NavigationDrawer(
                Modifier.padding(innerPadding),
                drawerState,
                scope,
            )
        }
    )
}
