package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PostsScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    navigateToUpdatePostScreen: (postId: Int) -> Unit
) {
    val posts by viewModel.postsState.collectAsState()

    /* TODO: Components */
}