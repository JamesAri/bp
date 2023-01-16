package cz.zcu.students.lostandfound.lost_items.presentation.update_lost_item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.lost_items.presentation.find_lost_item.LostItemViewModel

@Composable
fun UpdatePostScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    lostItemId: String,
    navigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getLostItem(lostItemId)
    }

    /* TODO: Components */
}