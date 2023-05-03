package cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item

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
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.ALL_IMAGES
import cz.zcu.students.lostandfound.common.constants.Navigation.Companion.NAVIGATION_LOCATION_KEY
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components.ImagePlaceholder
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope


/**
 * Screen for adding lost items.
 *
 * @param coroutineScope coroutine scope to run blocking code.
 * @param navigateToMarkLostItemScreen navigates to screen where user can
 *     mark lost item on a map.
 * @param navigateBack function to call to navigate one screen back.
 * @param navController app's navigation controller.
 */
@Composable
fun AddLostItemScreen(
    coroutineScope: CoroutineScope,
    navigateToMarkLostItemScreen: () -> Unit,
    navigateBack: () -> Unit,
    navController: NavHostController,
) {
    LostItemEditor(
        navigateToMarkLostItemScreen = navigateToMarkLostItemScreen,
        navController = navController
    )

    CreateLostItemListener(
        coroutineScope = coroutineScope,
        navigateBack = navigateBack
    )
}

/**
 * Component with states and lost item edit form.
 *
 * @param addLostItemViewModel viewmodel with add lost item utilities.
 * @param navController app's navigation controller.
 * @param navigateToMarkLostItemScreen navigates to screen where user can
 *     mark lost item on a map.
 */
@Composable
private fun LostItemEditor(
    addLostItemViewModel: AddLostItemViewModel = hiltViewModel(),
    navController: NavHostController,
    navigateToMarkLostItemScreen: () -> Unit,
) {

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { imageUri ->
            imageUri?.let { uri ->
                addLostItemViewModel.setItemUri(uri)
            }
        }

    LostItemForm(
        openGallery = {
            galleryLauncher.launch(ALL_IMAGES)
        },
        navigateToMarkLostItemScreen = navigateToMarkLostItemScreen,
        navController = navController,
    )
}

/**
 * Lost item edit form.
 *
 * @param modifier the modifier to be applied to the layout.
 * @param addLostItemViewModel viewmodel with add lost item utilities.
 * @param openGallery callback for opening gallery intent.
 * @param lostItemViewModel viewmodel for lost items.
 * @param navController app's navigation controller.
 * @param navigateToMarkLostItemScreen navigates to screen where user can
 *     mark lost item on a map.
 */
@Composable
private fun LostItemForm(
    modifier: Modifier = Modifier,
    addLostItemViewModel: AddLostItemViewModel = hiltViewModel(),
    openGallery: () -> Unit,
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    navController: NavHostController,
    navigateToMarkLostItemScreen: () -> Unit,
) {
    var requestRunning by remember { mutableStateOf(false) }
    val title = addLostItemViewModel.title
    val description = addLostItemViewModel.description
    val uriState = addLostItemViewModel.uriState
    val location = addLostItemViewModel.location

    val lostItemMarkLocationResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<List<Double>>(NAVIGATION_LOCATION_KEY)?.observeAsState()

    LaunchedEffect(lostItemMarkLocationResult) {
        val locationList = lostItemMarkLocationResult?.value
        if (locationList == null) {
            addLostItemViewModel.setItemLocation(null)
        } else if (locationList.size == 2) {
            addLostItemViewModel.setItemLocation(
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
            onValueChange = addLostItemViewModel::setItemTitle,
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
            onValueChange = addLostItemViewModel::setItemDescription,
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
                    lostItemViewModel.createLostItem(
                        title = title,
                        description = description,
                        localImageUri = uriState,
                        location = location,
                    )
                }) {
                Text(
                    text = stringResource(R.string.screen_lost_item_create_action)
                )
            }
        }
    }
}


/**
 * Listens for creation of lost item and shows appropriate snackbar message
 * on change.
 *
 * @param lostItemViewModel viewmodel for lost items.
 * @param coroutineScope coroutine scope to run blocking code.
 * @param navigateBack function to call to navigate one screen back.
 */
@Composable
private fun CreateLostItemListener(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
) {
    ResponseSnackBarHandler(
        response = lostItemViewModel.crudLostItemState,
        onTrueMessage = stringResource(R.string.screen_lost_item_create_success),
        onFalseMessage = stringResource(R.string.screen_lost_item_create_failure),
        snackbarHostState = LocalSnackbarHostState.current,
        coroutineScope = coroutineScope,
        onTrueAction = navigateBack,
    )
}

