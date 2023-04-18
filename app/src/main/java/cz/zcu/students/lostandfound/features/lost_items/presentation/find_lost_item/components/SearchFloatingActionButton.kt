package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.dialogs.FilterDialog

@Composable
fun SearchFloatingActionButton(
    onAddNewFilter: (String) -> Unit,
) {
    var openDialogState by remember { mutableStateOf(false) }

    ExtendedFloatingActionButton(
        onClick = {
            openDialogState = true
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(
                    R.string.screen_lost_item_search_lost_items_content_description
                )
            )
        },
        text = {
            Text(
                stringResource(
                    R.string.screen_lost_item_search_action
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

    FilterDialog(
        openDialogState = openDialogState,
        onDismissRequest = { openDialogState = false },
        onAddNewFilter = onAddNewFilter,
    )
}


