package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.constants.Validations.Companion.MAX_SEARCH_TERM_LENGTH
import cz.zcu.students.lostandfound.features.lost_items.domain.util.SearchTermValidator
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
        val termValidator = SearchTermValidator()

        val validationErrorMessage =
            stringResource(R.string.screen_lost_item_validation_message, MAX_SEARCH_TERM_LENGTH)

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
                        text = stringResource(R.string.screen_lost_item_add_new_filter_action),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

                    TextField(
                        value = searchTermState,
                        label = { Text(text = stringResource(R.string.screen_lost_item_add_term_action)) },
                        onValueChange = {
                            if (termValidator.validate(it))
                                searchTermState = it
                            if (it.length > MAX_SEARCH_TERM_LENGTH)
                                showValidationErrorState = true
                        },
                        singleLine = true,
                        placeholder = { Text(stringResource(R.string.screen_lost_item_search_action)) },
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
                            Text(text = stringResource(R.string.screen_lost_item_cancel_action))
                        }
                        TextButton(
                            enabled = searchTermState.isNotBlank(),
                            onClick = {
                                onDismissRequest()
                                onAddNewFilter(searchTermState)
                            }
                        ) {
                            Text(text = stringResource(R.string.screen_lost_item_add_action))
                        }
                    }
                }
            }
        }
    }
}
