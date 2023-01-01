package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.zcu.students.lostandfound.common.presentation.ImageCard
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun LostItemsScreen(
    navigateToUpdatePostScreen: (postId: Int) -> Unit
) {
    CardsView()
}

@Composable
fun CardsView() {
    LazyColumn() {
        items(20) {
            ImageCard(
                title = "Lorem ipsum",
                description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. In rutrum. Cras elementum. In convallis. Praesent vitae arcu tempor neque lacinia pretium. Nam quis nulla.",
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            )
        }
    }
}
