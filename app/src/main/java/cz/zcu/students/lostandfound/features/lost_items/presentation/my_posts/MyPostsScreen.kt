package cz.zcu.students.lostandfound.features.lost_items.presentation.my_posts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.util.getFormattedDateString
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing


@Composable
fun MyPostsScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    navigateToAddPosts: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.loadMyItems()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddPosts,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add new lost item"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MyPosts()
        }
    }
}

@Composable
fun EmptyLostItemList() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No Posts Yet",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}


@Composable
fun MyPosts(
    viewModel: LostItemViewModel = hiltViewModel(),
) {
    ResponseHandler(
        response = viewModel.lostItemListState.collectAsStateWithLifecycle().value,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = { lostItemList ->
            if (lostItemList.lostItems.isEmpty()) {
                EmptyLostItemList()
            } else {
                val lostItemsListLastIndex = lostItemList.lostItems.size - 1
                Column(
                    Modifier.fillMaxSize()
                ) {
                    LazyColumn {
                        itemsIndexed(lostItemList.lostItems) { index, lostItem ->
                            Divider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.primary
                            )
                            LostItemField(lostItem)
                            if (index == lostItemsListLastIndex) {
                                Divider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                            }
                        }
                    }
                }
            }
        },
        onSuccessNullContent = {
            EmptyLostItemList()
        }
    )
}

@Composable
fun LostItemField(
    lostItem: LostItem,
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(start = MaterialTheme.spacing.small)
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Text(
            text = lostItem.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth(.80f)
                .align(Alignment.CenterStart)
        )
        Text(
            text = lostItem.createdAt?.let { getFormattedDateString(it) } ?: "unknown date",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.TopEnd),
        )

        IconButton(
            onClick = { expanded = true },
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.BottomEnd),
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = { },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    })
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = { lostItemViewModel.deleteLostItem(lostItem) },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    })
            }
        }
    }
}

