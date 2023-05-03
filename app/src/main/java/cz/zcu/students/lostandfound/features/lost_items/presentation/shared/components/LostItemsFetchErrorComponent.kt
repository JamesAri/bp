package cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.zcu.students.lostandfound.R

/** Component with error message when loading of the lost items failed. */
@Composable
fun LostItemsFetchErrorComponent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(
                R.string.screen_lost_item_error_occurred_while_fetching_lost_items_message
            ),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
