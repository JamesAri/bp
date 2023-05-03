package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Chips with terms of the filtered lost items.
 *
 * @param viewModel lost items viewmodel.
 */
@Composable
fun ChipFilters(
    viewModel: LostItemViewModel = hiltViewModel()
) {
    FlowRow(
        mainAxisSize = SizeMode.Wrap,
        mainAxisSpacing = MaterialTheme.spacing.small,
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        viewModel.filters.forEach { filter ->
            InputChip(
                selected = false,
                onClick = {
                    viewModel.filters.remove(filter)
                    viewModel.loadLostItems()
                },
                label = {
                    Text(
                        text = filter,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(
                            R.string.screen_lost_item_remove_filter_content_description
                        )
                    )
                },
            )
        }
    }
}
