package cz.zcu.students.lostandfound.lost_items.presentation.find_lost_item

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.common.presentation.components.ImageCard
import cz.zcu.students.lostandfound.common.presentation.components.ProgressBar
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.lost_items.domain.lost_item.LostItemList
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.flow.Flow

@Composable
fun LostItemsScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    navigateToUpdatePostScreen: (postId: Int) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.loadLostItems()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
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
    val lostItemListState = lostItemsFlow.collectAsState(initial = LostItemList(listOf()))

    LazyColumn(
    ) {
        items(lostItemListState.value.lostItems) { item ->
            ImageCard(
                title = item.title,
                description = item.description,
                uri = item.imageUri,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            )
        }
    }
}
