package cz.zcu.students.lostandfound.navigation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.zcu.students.lostandfound.navigation.MappedRouteNames
import cz.zcu.students.lostandfound.navigation.NavItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavController,
) {
    val backStackEntryState = navController.currentBackStackEntryAsState()

    val screenTitleState by remember {
        derivedStateOf {
            backStackEntryState.value?.destination?.route?.let {
                MappedRouteNames.getValue(key = it.split('/')[0])
            } ?: ""
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(4.dp)
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth(0.95f),
            title = {
                Text(
                    text = screenTitleState,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isClosed)
                                drawerState.open()
                            else
                                drawerState.close()
                        }
                    })
                {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Open navigation drawer icon"
                    )
                }
            },
            actions = {
                val profileNavItem = NavItem.Profile
                IconButton(onClick = {
                    navController.navigate(profileNavItem.route)
                }) {
                    Icon(
                        imageVector = profileNavItem.icon.getIcon(),
                        contentDescription = profileNavItem.name
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        )
    }
}