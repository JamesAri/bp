package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.ChipFilters
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.ImageCard
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components.LostItemsFetchErrorComponent
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun FindLostItemScreen(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    navigateToLostItemDetail: (String) -> Unit,
    navigateToMapMarker: (Double, Double) -> Unit,
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
            LostItemsResponseContent(
                navigateToLostItemDetail = navigateToLostItemDetail,
                navigateToMapMarker = navigateToMapMarker
            )
        }
    }
}


@Composable
fun LostItemsResponseContent(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLostItemDetail: (String) -> Unit,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    ResponseHandler(
        response = lostItemViewModel.lostItemListState.collectAsStateWithLifecycle().value,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = {
            if (it.lostItems.isEmpty() && lostItemViewModel.filters.isNotEmpty()) {
                LostItemsFetchErrorComponent()
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
                            navigateToMapMarker = navigateToMapMarker
                        )
                    }
                }
            }
        },
        onSuccessNullContent = {
            LostItemsFetchErrorComponent()
        }
    )
}
