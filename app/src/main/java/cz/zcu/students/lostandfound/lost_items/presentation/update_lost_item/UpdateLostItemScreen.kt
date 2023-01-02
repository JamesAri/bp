package cz.zcu.students.lostandfound.lost_items.presentation.update_lost_item

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.lost_items.presentation.lost_items.LostItemViewModel

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