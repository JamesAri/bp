package cz.zcu.students.lostandfound.lost_items.presentation.update_lost_item

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.lost_items.presentation.lost_items.LostItemViewModel

@Composable
fun UpdatePostScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    postId: Int,
    navigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getLostItem(postId)
    }

    Log.i("zcu", "Updating ${viewModel.lostItemState.lostItem}")

    /* TODO: Components */
}