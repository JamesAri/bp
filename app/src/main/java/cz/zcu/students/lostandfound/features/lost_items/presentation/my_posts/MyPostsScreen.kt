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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.util.datetime.getFormattedDateString
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item.components.ConfirmDeleteDialog
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Screen with the list of all posts by the currently logged in user.
 *
 * @param lostItemViewModel lost items viewmodel.
 * @param navigateToAddPosts navigates to add post (lost item) screen.
 * @param navigateToUpdateScreen navigates to update lost item screen.
 */
@Composable
fun MyPostsScreen(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    navigateToAddPosts: () -> Unit,
    navigateToUpdateScreen: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        lostItemViewModel.loadMyItems()
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
                    contentDescription = stringResource(
                        R.string.screen_lost_item_add_new_lost_item_content_description
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MyPostsListener(navigateToUpdateScreen = navigateToUpdateScreen)
        }
    }
}

/** Empty lost item list component. */
@Composable
private fun EmptyLostItemList() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.screen_lost_item_no_posts_yet_placeholder_1))
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(stringResource(R.string.screen_lost_item_no_posts_yet_placeholder_2))
                }
                append(stringResource(R.string.screen_lost_item_no_posts_yet_placeholder_3))
            },
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
    }
}

/** Post divider component. */
@Composable
private fun PostDivider() {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        thickness = 2.dp,
    )
}

/**
 * Renders editable lost item list of the current user posts.
 *
 * @param lostItemList current user posts.
 * @param navigateToUpdateScreen navigates to update screen of the passed
 *     lost item id.
 */
@Composable
private fun RenderEditableLostItemList(
    lostItemList: LostItemList,
    navigateToUpdateScreen: (String) -> Unit,
) {
    if (lostItemList.lostItems.isEmpty()) {
        EmptyLostItemList()
    } else {
        val lostItemsListLastIndex = lostItemList.lostItems.size - 1
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = MaterialTheme.spacing.medium)
        ) {
            itemsIndexed(lostItemList.lostItems) { index, lostItem ->
                PostDivider()
                LostItemField(
                    lostItem = lostItem,
                    navigateToUpdateScreen = navigateToUpdateScreen,
                )
                if (index == lostItemsListLastIndex) {
                    PostDivider()
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                }
            }
        }
    }

}

/**
 * Listens for current owner's posts changes and shows appropriate snackbar
 * message on change.
 *
 * @param lostItemViewModel lost items viewmodel.
 * @param navigateToUpdateScreen navigates to update screen of the passed
 *     lost item id.
 */
@Composable
private fun MyPostsListener(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    navigateToUpdateScreen: (String) -> Unit,
) {
    ResponseHandler(
        response = lostItemViewModel.lostItemListState.collectAsStateWithLifecycle().value,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = { lostItemList ->
            RenderEditableLostItemList(
                lostItemList = lostItemList,
                navigateToUpdateScreen = navigateToUpdateScreen,
            )
        },
        onSuccessNullContent = {
            EmptyLostItemList()
        }
    )
}

/**
 * Dropdown menu with actions like edit and delete.
 *
 * @param modifier the modifier to be applied to the layout.
 * @param expanded `true` if the dropdown menu is expanded, `false`
 *     otherwise.
 * @param onExpandRequest action on expand request.
 * @param onCloseRequest action on close request.
 * @param onEditRequest action on edit request.
 * @param onDeleteRequest action on delete request.
 */
@Composable
private fun EditPostDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandRequest: () -> Unit,
    onCloseRequest: () -> Unit,
    onEditRequest: () -> Unit,
    onDeleteRequest: () -> Unit,
) {
    IconButton(
        onClick = onExpandRequest,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    ) {
        Icon(
            Icons.Default.MoreVert,
            contentDescription = stringResource(
                R.string.screen_lost_item_localized_description_content_description
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onCloseRequest,
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.screen_lost_item_edit_action)) },
                onClick = onEditRequest,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null
                    )
                })
            DropdownMenuItem(
                text = { Text(stringResource(R.string.screen_lost_item_delete_action)) },
                onClick = onDeleteRequest,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null
                    )
                })
        }
    }
}

/**
 * One field of lost item in the list.
 *
 * @param lostItem to display.
 * @param lostItemViewModel lost items viewmodel.
 * @param navigateToUpdateScreen navigates to update lost item screen.
 */
@Composable
private fun LostItemField(
    lostItem: LostItem,
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    navigateToUpdateScreen: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var openDeleteDialogState by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
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
            text = lostItem.createdAt?.let {
                getFormattedDateString(
                    it,
                    lostItemViewModel.getLocaleTimeString(context)
                )
            }
                ?: stringResource(R.string.screen_lost_item_unknown_date),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.TopEnd),
        )
        EditPostDropdownMenu(
            modifier = Modifier.align(Alignment.BottomEnd),
            expanded = expanded,
            onExpandRequest = { expanded = true },
            onCloseRequest = { expanded = false },
            onDeleteRequest = { openDeleteDialogState = true },
            onEditRequest = {
                navigateToUpdateScreen(lostItem.id)
                expanded = false
            },
        )
    }

    ConfirmDeleteDialog(
        openDialogState = openDeleteDialogState,
        onDismissRequest = { openDeleteDialogState = false },
        onConfirmRequest = {
            openDeleteDialogState = false
            expanded = false
            lostItemViewModel.deleteLostItem(lostItem)
        },
        title = stringResource(R.string.screen_lost_item_deleting_lost_item_dialog_title),
        text = stringResource(
            R.string.screen_lost_item_deleting_lost_item_dialog_body,
            lostItem.title
        )
    )
}

