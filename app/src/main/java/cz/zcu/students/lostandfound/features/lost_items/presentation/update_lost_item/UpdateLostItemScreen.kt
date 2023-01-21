package cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel

@Composable
fun UpdatePostScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    lostItemId: String?,
) {
    LaunchedEffect(Unit) {
        if(lostItemId != null) {
            viewModel.getLostItem(lostItemId)
        }
    }

    Text(text = "Hello there")
}