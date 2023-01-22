package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.ui.theme.spacing

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
                        contentDescription = "remove filter"
                    )
                },
            )
        }
    }
}
