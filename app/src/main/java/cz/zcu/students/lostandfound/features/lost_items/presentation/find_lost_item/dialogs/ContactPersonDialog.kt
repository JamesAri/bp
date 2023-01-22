package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun ContactPersonDialog(
    openDialogState: Boolean,
    onDismissRequest: () -> Unit,
    postOwner: User,
) {
    if (openDialogState) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Contact person") },
            text = {
                Column() {
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
                        label = { Text(text = "Email") },
                    )
                    postOwner.phoneNumber?.let {
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        TextField(
                            value = postOwner.phoneNumber,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = "Phone number") },
                        )
                    }
                }
            },
            confirmButton = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "contact post owner",
                )
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                ) {
                    Text("Close")
                }
            }
        )
    }
}