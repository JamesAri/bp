package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

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
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun FindLostItemScreen(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    navigateToLostItemDetail: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        lostItemViewModel.loadLostItems()
    }

    Scaffold(
        floatingActionButton = {
            val filters = lostItemViewModel.filters
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
                .padding(paddingValues),
        ) {
            ChipFilters()
            LostItemsResponseContent(navigateToLostItemDetail = navigateToLostItemDetail)
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
            text = "No Items Found",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun LostItemsResponseContent(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLostItemDetail: (String) -> Unit,
) {
    ResponseHandler(
        response = lostItemViewModel.lostItemListState.collectAsStateWithLifecycle().value,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = {
            if (it.lostItems.isEmpty() && lostItemViewModel.filters.isNotEmpty()) {
                EmptyLostItemList()
            } else {
                var pairedLostItems by remember {
                    mutableStateOf<List<Pair<LostItem, User>>>(listOf())
                }

                LaunchedEffect(it.lostItems) {
                    pairedLostItems = it.lostItems.mapNotNull { lostItem ->
                        val postOwner = authViewModel.getUser(lostItem.postOwnerId)
                        postOwner?.let { user ->
                            Pair(lostItem, user)
                        }
                    }
                }

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
        },
        onSuccessNullContent = {
            EmptyLostItemList()
        }
    )
}
