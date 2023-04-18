package cz.zcu.students.lostandfound.features.lost_items.presentation.lost_item_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.dialogs.ContactPersonDialog
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components.ContactAndMapMarkerAssistChips
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun LostItemDetailScreen(
    lostItemId: String?,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    if (lostItemId != null) {
        LoadLostItem(lostItemId = lostItemId, navigateToMapMarker = navigateToMapMarker)
    } else {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.screen_lost_item_couldnt_load_items_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun LoadLostItem(
    viewModel: LostItemViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    lostItemId: String,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getLostItem(lostItemId)
    }

    ResponseHandler(
        response = viewModel.lostItemState,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = { lostItem ->
            var postOwner by remember { mutableStateOf<User?>(null) }
            LaunchedEffect(Unit) {
                postOwner = authViewModel.getUser(lostItem.postOwnerId)
            }
            postOwner?.let { user ->
                LostItemDetail(
                    lostItem = lostItem,
                    postOwner = user,
                    navigateToMapMarker = navigateToMapMarker,
                )
            }
        }
    )
}

@Composable
fun LostItemDetail(
    lostItem: LostItem,
    postOwner: User,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    var openDialogState by remember { mutableStateOf(false) }

    val latitude = lostItem.location?.latitude
    val longitude = lostItem.location?.longitude
    val locationProvided = latitude != null && longitude != null

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            Text(
                text = stringResource(R.string.screen_lost_item_lost_item),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AsyncImage(
                model = lostItem.imageUri,
                contentDescription = lostItem.title,
                error = painterResource(id = R.drawable.no_image_placeholder),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Divider(
                thickness = 2.dp,
                modifier = Modifier.padding(
                    vertical = MaterialTheme.spacing.large,
                )
            )
            Text(
                text = stringResource(R.string.screen_lost_item_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = lostItem.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Divider(
                thickness = 2.dp,
                modifier = Modifier.padding(
                    vertical = MaterialTheme.spacing.large,
                )
            )
            Text(
                text = stringResource(R.string.screen_lost_item_description),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = lostItem.description,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ContactAndMapMarkerAssistChips(
                modifier = Modifier.fillMaxWidth(),
                mainAxisAlignment = FlowMainAxisAlignment.SpaceAround,
                onContactPerson = { openDialogState = true },
                onShowMapMarker = {
                    if (locationProvided) {
                        navigateToMapMarker(latitude!!, longitude!!)
                    }
                },
                locationProvided = locationProvided
            )
        }
    }

    ContactPersonDialog(
        openDialogState = openDialogState,
        onDismissRequest = { openDialogState = false },
        postOwner = postOwner
    )
}

