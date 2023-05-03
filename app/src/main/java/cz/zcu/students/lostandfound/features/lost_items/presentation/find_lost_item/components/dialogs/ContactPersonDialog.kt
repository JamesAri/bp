package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Shows contact dialog of the [postOwner].
 *
 * @param openDialogState dialog open state.
 * @param onDismissRequest to call when dialog dismiss requested.
 * @param postOwner [User] who is owner of the post.
 */
@Composable
fun ContactPersonDialog(
    openDialogState: Boolean,
    onDismissRequest: () -> Unit,
    postOwner: User,
) {
    if (openDialogState) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = stringResource(R.string.screen_lost_item_contact_person_action)) },
            text = {
                Column {
                    Text(
                        text = postOwner.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    TextField(
                        value = postOwner.email,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = stringResource(R.string.screen_lost_item_email)) },
                    )
                    postOwner.phoneNumber?.let {
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        TextField(
                            value = postOwner.phoneNumber,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = stringResource(R.string.screen_lost_item_phone_number)) },
                        )
                    }
                }
            },
            confirmButton = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(
                        R.string.screen_lost_item_contact_post_owner_content_description
                    ),
                )
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                ) {
                    Text(stringResource(R.string.screen_lost_item_close_action))
                }
            }
        )
    }
}