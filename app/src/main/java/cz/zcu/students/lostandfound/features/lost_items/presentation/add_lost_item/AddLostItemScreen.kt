package cz.zcu.students.lostandfound.features.lost_items.presentation.add_lost_item

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.common.constants.General
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.LostItemViewModel
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing


@Composable
fun AddLostItemScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
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

    LostItemForm(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        openGallery = {
            galleryLauncher.launch(General.ALL_IMAGES)
        },
        onCreateItem = { lostItem ->
            viewModel.createLostItem(
                lostItem = lostItem,
                imageUri = uriState
            )
        }
    )
    CreateLostItem()
}

@Composable
fun LostItemForm(
    modifier: Modifier = Modifier,
    openGallery: () -> Unit,
    onCreateItem: (lostItem: LostItem) -> Unit,
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
            },
            singleLine = true,
            label = {
                Text("Description")
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = {
                    onCreateItem(
                        LostItem(
                            title = title,
                            description = description,
                        )
                    )
                }) {
                Text(text = "Create")
            }

            Button(onClick = openGallery) {
                Text("Load image")
            }
        }
    }
}


@Composable
fun CreateLostItem(
    viewModel: LostItemViewModel = hiltViewModel(),
) {
    ResponseSnackBarHandler(
        response = viewModel.createdLostItemState,
        onTrueMessage = "Successfully created new item",
        onFalseMessage = "Failed to create new item",
        snackbarHostState = LocalSnackbarHostState.current,
    )
}

