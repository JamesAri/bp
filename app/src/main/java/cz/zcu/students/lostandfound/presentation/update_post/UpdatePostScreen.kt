package cz.zcu.students.lostandfound.presentation.update_post

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.presentation.posts.PostsViewModel

@Composable
fun UpdatePostScreen(
    viewModel: PostsViewModel = hiltViewModel(),
    postId: Int,
    navigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getPost(postId)
    }

    Log.i("zcu", "Updating ${viewModel.postState.post}")

    /* TODO: Components */
}