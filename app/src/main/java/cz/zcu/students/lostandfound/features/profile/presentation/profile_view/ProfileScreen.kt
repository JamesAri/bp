package cz.zcu.students.lostandfound.features.profile.presentation.profile_view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.features.profile.presentation.profile_view.components.EditableProfileImageIcon
import cz.zcu.students.lostandfound.features.profile.presentation.profile_view.components.ProfileField
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Profile screen.
 *
 * @param navigateToLoginScreen navigates to login screen (when user is logged out).
 * @param navigateToChangePhoneNumber navigates to change phone number form screen.
 */
@Composable
fun ProfileScreen(
    navigateToLoginScreen: () -> Unit,
    navigateToChangePhoneNumber: () -> Unit,
) {
    LoadUserPreview(
        navigateToLoginScreen = navigateToLoginScreen,
        navigateToChangePhoneNumber = navigateToChangePhoneNumber,
    )
}

/**
 * Loads and displays user info page (screen).
 *
 * @param authViewModel
 * @param navigateToLoginScreen
 * @param navigateToChangePhoneNumber
 */
@Composable
private fun LoadUserPreview(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToChangePhoneNumber: () -> Unit,
) {
    ResponseHandler(
        response = authViewModel.currentUser,
        snackbarHostState = LocalSnackbarHostState.current
    ) { user ->
        UserDataView(
            user = user,
            navigateToLoginScreen = navigateToLoginScreen,
            navigateToChangePhoneNumber = navigateToChangePhoneNumber,
        )
    }
}

/**
 * Composable with user data like email, phone number, name, etc..
 *
 * @param user user to display.
 * @param authViewModel authentication viewmodel.
 * @param navigateToLoginScreen navigates to login screen (when user is logged out).
 * @param navigateToChangePhoneNumber navigates to change phone number form screen.
 */
@Composable
private fun UserDataView(
    user: User,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToChangePhoneNumber: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        EditableProfileImageIcon(user)

        ProfileField(
            icon = Icons.Default.Person,
            title = stringResource(R.string.screen_profile_name),
            value = user.name,
        )

        ProfileField(
            icon = Icons.Default.Email,
            title = stringResource(R.string.screen_profile_email),
            value = user.email,
        )

        val phoneNumber =
            if (user.phoneNumber.isNullOrBlank())
                stringResource(R.string.screen_profile_not_added_yet)
            else user.phoneNumber

        ProfileField(
            icon = Icons.Default.Phone,
            title = stringResource(R.string.screen_profile_phone_number),
            value = phoneNumber,
        ) {
            TextButton(
                onClick = navigateToChangePhoneNumber,
            ) {
                Text(text = stringResource(R.string.screen_profile_change_action))
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(MaterialTheme.spacing.medium),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                authViewModel.logout()
                navigateToLoginScreen()
            }) {
            Text(text = stringResource(R.string.screen_profile_logout_action))
        }
    }
}

