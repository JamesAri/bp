package cz.zcu.students.lostandfound.features.lost_items.presentation.my_posts

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyPostsScreen(
    navigateToAddPosts: () -> Unit,
) {
    Button(onClick = navigateToAddPosts) {
        Text(text = "Add post")
    }
}