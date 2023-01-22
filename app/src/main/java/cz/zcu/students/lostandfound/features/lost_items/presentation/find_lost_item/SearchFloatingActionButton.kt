package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.dialogs.FilterDialog

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
                contentDescription = "search floating action button"
            )
        },
        text = { Text("Search") },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

    FilterDialog(
        openDialogState = openDialogState,
        onDismissRequest = { openDialogState = false },
        onAddNewFilter = onAddNewFilter,
    )
}


