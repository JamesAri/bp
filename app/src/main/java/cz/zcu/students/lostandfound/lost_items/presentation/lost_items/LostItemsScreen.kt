package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.lost_items.domain.location.LocationCoordinates
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem

@Composable
fun PostsScreen(
    navigateToUpdatePostScreen: (postId: Int) -> Unit
) {
    RealtimeObserving()
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RealtimeObserving(
    modifier: Modifier = Modifier,
    viewModel: LostItemViewModel = hiltViewModel(),
) {
    val lostItems by viewModel.lostItemsState.collectAsStateWithLifecycle()

    Column(
        modifier.fillMaxSize(),
    ) {


        if (lostItems.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Red,
                )
            }
        }

        lostItems.error?.let { error ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(),
        ) {

            Button(onClick = {
                viewModel.createLostItem(
                    LostItem(
                        title = "Lost item title",
                        description = "Lost item description",
                        location = LocationCoordinates(.0, .0)
                    )
                )
            }) {
                Text(text = "Create new lost item")
            }

            Button(onClick = viewModel::loadLostItems) {
                Text("Start listening")
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {

            val data = lostItems.data?.collectAsState(initial = listOf())

            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp),
            ) {
                if (data?.value != null) {
                    items(data.value) { item ->
                        Text(text = "${item.title} - ${item.description}")
                    }
                }
            }
        }
    }
}
