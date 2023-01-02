package cz.zcu.students.lostandfound.lost_items.presentation.lost_items

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.common.Constants.Companion.ALL_IMAGES
import cz.zcu.students.lostandfound.common.presentation.components.ProgressBar
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItemList
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

@Composable
fun LostItemsScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    navigateToUpdatePostScreen: (postId: Int) -> Unit
) {
    var uriState by remember {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            imageUri?.let {
                uriState = it
            }
        }

    Column {

        ListeningControls(
            openGallery = {
                galleryLauncher.launch(ALL_IMAGES)
            },
            onCreateItem = { lostItem ->
                viewModel.createLostItem(
                    lostItem = lostItem,
                    imageUri = uriState
                )
            }
        )

        LoadLostItems()
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoadLostItems(
    viewModel: LostItemViewModel = hiltViewModel(),
) {
    when (val loadLostItemsFromDatabaseResponse =
        viewModel.lostItemsState.collectAsStateWithLifecycle().value) {

        is Loading -> ProgressBar()
        is Success -> loadLostItemsFromDatabaseResponse.data?.let { lostItemsFlow ->
            LostItemCards(lostItemsFlow)
        }
        is Error -> LaunchedEffect(Unit) {
            Log.d(
                "LoadLostItems",
                "Error: ${loadLostItemsFromDatabaseResponse.error.message}"
            )
        }
    }
}

@Composable
fun LostItemCards(
    lostItemsFlow: Flow<LostItemList>
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val lostItemListState = lostItemsFlow.collectAsState(initial = LostItemList(listOf()))

        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            items(lostItemListState.value.lostItems) { item ->
                Text(text = "${item.title} - ${item.description}")
            }
        }
    }

}

@Composable
fun ListeningControls(
    modifier: Modifier = Modifier,
    viewModel: LostItemViewModel = hiltViewModel(),
    openGallery: () -> Unit,
    onCreateItem: (lostItem: LostItem) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = {
            onCreateItem(
                LostItem(
                    title = "Lost item title - ${Random(0).nextInt()}",
                )
            )
        }) {
            Text(text = "Create")
        }

        Button(onClick = viewModel::loadLostItems) {
            Text("Start listening")
        }

        Button(onClick = openGallery) {
            Text("Load image")
        }
    }
}



