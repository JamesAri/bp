package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.common.components.ProgressBar
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.common.util.Response.*
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItemList
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun FindLostItemScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    navigateToLostItemDetail: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.loadLostItems()
    }

    LoadLostItems(
        navigateToLostItemDetail = navigateToLostItemDetail,
    )
}

@Composable
fun LoadLostItems(
    viewModel: LostItemViewModel = hiltViewModel(),
    navigateToLostItemDetail: (String) -> Unit,
) {
    when (val loadLostItemListResponse =
        viewModel.lostItemListState.collectAsStateWithLifecycle().value) {
        is Loading -> ProgressBar()
        is Success -> loadLostItemListResponse.data?.let { lostItemList ->
            LostItemCards(
                lostItemList = lostItemList,
                navigateToLostItemDetail = navigateToLostItemDetail,
            )
        }
        is Error -> LaunchedEffect(Unit) {
            Log.d(
                "LoadLostItems",
                "Error: ${loadLostItemListResponse.error.message}"
            )
        }
    }
}

@Composable
fun EmptyLostItemList() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No item found",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun LostItemCards(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    lostItemList: LostItemList,
    navigateToLostItemDetail: (String) -> Unit,
) {

    var pairedLostItems by remember {
        mutableStateOf<List<Pair<LostItem, User>>>(listOf())
    }

    LaunchedEffect(lostItemList.lostItems) {
        pairedLostItems = lostItemList.lostItems.mapNotNull { lostItem ->
            val postOwner = authViewModel.getUser(lostItem.postOwnerId)
            postOwner?.let { user ->
                Pair(lostItem, user)
            }
        }
    }

    val filters = lostItemViewModel.filters
    Scaffold(
        floatingActionButton = {
            SearchFloatingActionButton(
                onAddNewFilter = {
                    if (filters.isEmpty() || it !in filters) {
                        filters.add(it)
                        lostItemViewModel.loadLostItems()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ChipFilters()
            if (pairedLostItems.isEmpty() && filters.isNotEmpty()) {
                EmptyLostItemList()
            } else {
                LazyColumn {
                    items(pairedLostItems) { item ->
                        ImageCard(
                            lostItemData = item,
                            modifier = Modifier.padding(MaterialTheme.spacing.medium),
                            navigateToLostItemDetail = navigateToLostItemDetail,
                        )
                    }
                }
            }
        }
    }
}
