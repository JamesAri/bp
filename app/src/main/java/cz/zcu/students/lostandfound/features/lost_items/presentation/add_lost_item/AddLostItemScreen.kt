package cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.ALL_IMAGES
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope


@Composable
fun AddLostItemScreen(
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
) {
    LostItemEditor()

    CreateLostItemListener(
        coroutineScope = coroutineScope,
        navigateBack = navigateBack
    )
}

@Composable
fun LostItemEditor(
) {
    var uriState by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            imageUri?.let {
                uriState = it
            }
        }

    LostItemForm(
        openGallery = {
            galleryLauncher.launch(ALL_IMAGES)
        },
        uriState = uriState,
    )
}

@Composable
fun LostItemForm(
    modifier: Modifier = Modifier,
    openGallery: () -> Unit,
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    uriState: Uri?,
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var requestRunning by remember { mutableStateOf(false) }

    val enabled =
        description.isNotEmpty() && title.isNotEmpty() && (uriState != null) && !requestRunning

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.SpaceAround,
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

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                title = it
            },
            singleLine = true,
            label = {
                Text("Title")
            }
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f),
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text("Description")
            }
        )
        Text(
            text = "All fields must be filled",
            style = MaterialTheme.typography.labelMedium,
        )


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

