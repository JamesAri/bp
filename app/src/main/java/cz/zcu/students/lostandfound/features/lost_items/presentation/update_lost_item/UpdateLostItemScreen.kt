package cz.zcu.students.lostandfound.features.lost_items.presentation.update_lost_item

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.ALL_IMAGES
import cz.zcu.students.lostandfound.common.constants.Navigation
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components.ImagePlaceholder
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope

/**
 * Update lost item screen.
 *
 * @param updateLostItemViewModel update lost item viewmodel.
 * @param lostItemId lost item id.
 * @param coroutineScope coroutine scope to run blocking code.
 * @param navigateBack navigates back one screen in the navigation.
 * @param navController app's navigation controller.
 * @param navigateToMarkLostItemScreen navigates to mark lost item screen,
 *     where user can change the location of the found item.
 */
@Composable
fun UpdatePostScreen(
    updateLostItemViewModel: UpdateLostItemViewModel = hiltViewModel(),
    lostItemId: String?,
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
    navController: NavHostController,
    navigateToMarkLostItemScreen: () -> Unit,
) {

    LaunchedEffect(Unit) {
        if (lostItemId != null) {
            updateLostItemViewModel.getLostItem(lostItemId)
        }
    }

    ResponseHandler(response = updateLostItemViewModel.lostItemState,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = { lostItem ->
            LostItemEditor(
                navController = navController,
                navigateToMarkLostItemScreen = navigateToMarkLostItemScreen,
                lostItem = lostItem,
            )
        }
    )

    UpdateLostItemListener(
        navigateBack = navigateBack,
        coroutineScope = coroutineScope,
    )
}

/**
 * Editor component with the passed [lostItem]. Functions as intent
 * launcher and form renderer.
 *
 * @param updateLostItemViewModel update lost item viewmodel.
 * @param navController app's navigation controller.
 * @param lostItem to edit.
 * @param navigateToMarkLostItemScreen navigates to mark lost item screen,
 *     where user can change the location of the found item.
 */
@Composable
private fun LostItemEditor(
    updateLostItemViewModel: UpdateLostItemViewModel = hiltViewModel(),
    navController: NavHostController,
    lostItem: LostItem,
    navigateToMarkLostItemScreen: () -> Unit,
) {
    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { imageUri ->
            imageUri?.let { uri ->
                updateLostItemViewModel.setItemUri(uri)
            }
        }

    LostItemForm(
        openGallery = {
            galleryLauncher.launch(ALL_IMAGES)
        },
        navigateToMarkLostItemScreen = navigateToMarkLostItemScreen,
        navController = navController,
        lostItem = lostItem,
    )
}

/**
 * Lost item form to update the passed lost item [lostItem].
 *
 * @param modifier
 * @param updateLostItemViewModel update lost item viewmodel.
 * @param lostItemViewModel lostItemViewModel lost items viewmodel.
 * @param openGallery function to call to open gallery with intent.
 * @param lostItem to edit.
 * @param navController app's navigation controller.
 * @param navigateToMarkLostItemScreen navigates to mark lost item screen,
 *     where user can change the location of the found item.
 */
@Composable
private fun LostItemForm(
    modifier: Modifier = Modifier,
    updateLostItemViewModel: UpdateLostItemViewModel = hiltViewModel(),
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    openGallery: () -> Unit,
    lostItem: LostItem,
    navController: NavHostController,
    navigateToMarkLostItemScreen: () -> Unit,
) {
    var requestRunning by remember { mutableStateOf(false) }
    val title = updateLostItemViewModel.title
    val description = updateLostItemViewModel.description
    val uriState = updateLostItemViewModel.uriState
    val location = updateLostItemViewModel.location

    val lostItemMarkLocationResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<List<Double>>(Navigation.NAVIGATION_LOCATION_KEY)?.observeAsState()

    LaunchedEffect(lostItemMarkLocationResult) {
        val locationList = lostItemMarkLocationResult?.value
        if (locationList != null && locationList.size == 2) {
            updateLostItemViewModel.setItemLocation(
                LocationCoordinates(
                    locationList[0],
                    locationList[1]
                )
            )
        }
    }

    val enabled =
        description.isNotEmpty() && title.isNotEmpty() && (uriState != null) && !requestRunning

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.SpaceAround,
    ) {

        ImagePlaceholder(
            openGallery = openGallery,
            uriState = uriState,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            shape = RectangleShape,
            onValueChange = updateLostItemViewModel::setItemTitle,
            singleLine = true,
            label = {
                Text(stringResource(R.string.screen_lost_item_title))
            }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RectangleShape,
            value = description,
            onValueChange = updateLostItemViewModel::setItemDescription,
            label = {
                Text(stringResource(R.string.screen_lost_item_description))
            }
        )
        Text(
            text = stringResource(R.string.screen_lost_item_all_fields_must_be_filled_note),
            style = MaterialTheme.typography.labelMedium,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Button(onClick = navigateToMarkLostItemScreen) {
            Text(
                text = if (location == null) stringResource(R.string.screen_lost_item_mark_item_action)
                else stringResource(R.string.screen_lost_item_item_marked)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Button(enabled = enabled,
                onClick = {
                    requestRunning = true
                    lostItemViewModel.updateLostItem(
                        lostItem.copy(
                            title = title,
                            description = description,
                            imageUri = uriState,
                            location = location,
                        ),
                        // since we are updating, it means the items was created once,
                        // thus if the current uri matches previous uri, it is remote uri.
                        hasRemoteUri = lostItem.imageUri == uriState
                    )
                }) {
                Text(
                    text = stringResource(R.string.screen_lost_item_update_action)
                )
            }
        }
    }
}

/**
 * Listens for lost item update changes and shows appropriate snackbar
 * message on change.
 *
 * @param lostItemViewModel lost items viewmodel.
 * @param coroutineScope coroutine scope to run blocking code.
 * @param navigateBack navigates back one screen in the navigation.
 */
@Composable
private fun UpdateLostItemListener(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
) {
    ResponseSnackBarHandler(
        response = lostItemViewModel.crudLostItemState,
        onTrueMessage = stringResource(R.string.screen_lost_item_update_success),
        onFalseMessage = stringResource(R.string.screen_lost_item_update_failur),
        snackbarHostState = LocalSnackbarHostState.current,
        coroutineScope = coroutineScope,
        onTrueAction = { navigateBack() },
    )
}

