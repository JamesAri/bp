package cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.zcu.students.lostandfound.R

/**
 * General confirm delete dialog.
 *
 * @param openDialogState dialog open state.
 * @param onDismissRequest to call when dismiss is requested.
 * @param onConfirmRequest to call when delete is confirmed.
 * @param title of the dialog.
 * @param text description/text of the dialog. Should contain info about
 *     the deleting item.
 */
@Composable
fun ConfirmDeleteDialog(
    openDialogState: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
    title: String,
    text: String,
) {

    if (openDialogState) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            icon = { Icon(Icons.Filled.Delete, contentDescription = title) },
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                TextButton(
                    onClick = onConfirmRequest
                ) {
                    Text(stringResource(R.string.screen_lost_item_confirm_action))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(stringResource(R.string.screen_lost_item_cancel_action))
                }
            }
        )
    }
}
