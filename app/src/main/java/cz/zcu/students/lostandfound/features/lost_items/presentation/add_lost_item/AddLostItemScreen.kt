package cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.ALL_IMAGES
import cz.zcu.students.lostandfound.common.constants.Navigation.Companion.NAVIGATION_LOCATION_KEY
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.common.features.map.domain.location_coordinates.LocationCoordinates
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope


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

@Composable
fun LostItemEditor(
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

@Composable
fun ImagePlaceholder(
    openGallery: () -> Unit,
    uriState: Uri?,
) {
    Box(
        modifier = Modifier
            .clickable(onClick = openGallery)
            .wrapContentSize()
    ) {
        if (uriState != null) {
            AsyncImage(
                model = uriState,
                contentDescription = "add lost item image",
                error = painterResource(id = R.drawable.no_image_placeholder),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Choose an image",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

}

@Composable
fun LostItemForm(
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
            addLostItemViewModel.setItemLocation(LocationCoordinates(locationList[0], locationList[1]))
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
                Text("Title")
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
                Text("Description")
            }
        )
        Text(
            text = "All fields must be filled",
            style = MaterialTheme.typography.labelMedium,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Button(onClick = navigateToMarkLostItemScreen) {
            Text(
                text = if (location == null) "Mark item on map" else "Item marked"
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
                    text = "Create"
                )
            }
        }
    }
}


@Composable
fun CreateLostItemListener(
    viewModel: LostItemViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
) {
    ResponseSnackBarHandler(
        response = viewModel.crudLostItemState,
        onTrueMessage = "Successfully created new item",
        onFalseMessage = "Failed to create new item",
        snackbarHostState = LocalSnackbarHostState.current,
        coroutineScope = coroutineScope,
        onTrueAction = navigateBack,
    )
}

