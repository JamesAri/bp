package cz.zcu.students.lostandfound.features.profile.presentation.profile_view.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Firebase
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState

/**
 * Composable that handles rendering and editing of the profile image.
 *
 * @param user user to display.
 * @param authViewModel authentication viewmodel.
 */
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

/**
 * Listens for profile changes and shows appropriate snackbar message on change.
 *
 * @param authViewModel
 */
@Composable
private fun UpdateProfileListener(
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    ResponseSnackBarHandler(
        response = authViewModel.updateCurrentUserStatus,
        onTrueMessage = stringResource(R.string.screen_profile_update_picture_success),
        onFalseMessage = stringResource(R.string.screen_profile_update_picture_failure),
        snackbarHostState = LocalSnackbarHostState.current,
    )
}