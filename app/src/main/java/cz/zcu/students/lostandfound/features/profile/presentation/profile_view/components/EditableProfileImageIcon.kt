package cz.zcu.students.lostandfound.features.profile.presentation.profile_view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Firebase
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState

@Composable
fun EditableProfileImageIcon(
    user: User,
    authViewModel: AuthViewModel = hiltViewModel()
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

    LaunchedEffect(uriState) {
        uriState?.let {
            authViewModel.updateCurrentUserProfilePicture(it)
        }
    }

    ProfileImageIcon(
        photoUri = user.photoUri,
        onEditClick = {
            galleryLauncher.launch(Firebase.ALL_IMAGES)
        }
    )

    UpdateProfileListener()
}

@Composable
fun UpdateProfileListener(
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    ResponseSnackBarHandler(
        response = authViewModel.updateCurrentUserStatus,
        onTrueMessage = "Successfully updated profile picture",
        onFalseMessage = "Failed to update profile picture",
        snackbarHostState = LocalSnackbarHostState.current,
    )
}