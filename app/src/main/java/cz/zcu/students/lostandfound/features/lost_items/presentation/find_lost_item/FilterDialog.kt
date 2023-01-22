package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.zcu.students.lostandfound.common.constants.General
import cz.zcu.students.lostandfound.features.lost_items.data.validators.SearchTermValidatorImpl
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun FilterDialog(
    openDialogState: Boolean,
    onDismissRequest: () -> Unit,
    onAddNewFilter: (String) -> Unit,
) {
    if (openDialogState) {
        var searchTermState by remember { mutableStateOf("") }
        var showValidationErrorState by remember { mutableStateOf(false) }
        val termValidator = SearchTermValidatorImpl()

        val validationErrorMessage = "Term cannot have more than ${General.MAX_SEARCH_TERM_LENGTH} characters"

        AlertDialog(
            onDismissRequest = onDismissRequest,
        ) {
            Surface(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Add a new filter",
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                    TextField(
                        value = searchTermState,
                        onValueChange = {
                            if (termValidator.validate(it))
                                searchTermState = it
                            if (it.length > General.MAX_SEARCH_TERM_LENGTH)
                                showValidationErrorState = true
                        },
                        singleLine = true,
                        placeholder = { Text("Search") },
                    )
                    Text(
                        text = if (showValidationErrorState) validationErrorMessage else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextButton(onClick = onDismissRequest) {
                            Text(text = "Cancel")
                        }
                        TextButton(
                            enabled = searchTermState.isNotBlank(),
                            onClick = {
                                onDismissRequest()
                                onAddNewFilter(searchTermState)
                            }
                        ) {
                            Text(text = "Add")
                        }
                    }
                }
            }
        }
    }
}
