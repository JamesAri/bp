package cz.zcu.students.lostandfound.features.profile.presentation.profile_view

import androidx.compose.foundation.layout.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.features.profile.presentation.profile_view.components.ProfileField
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

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

@Composable
fun LoadUserPreview(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToChangePhoneNumber: () -> Unit,
) {
    ResponseHandler(
        response = authViewModel.currentUser,
        snackbarHostState = LocalSnackbarHostState.current
    ) { user ->
        DisplayUserData(
            user = user,
            navigateToLoginScreen = navigateToLoginScreen,
            navigateToChangePhoneNumber = navigateToChangePhoneNumber,
        )
    }
}


@Composable
fun DisplayUserData(
    user: User,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToChangePhoneNumber: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            EditableProfileImageIcon(user)

            ProfileField(
                icon = Icons.Default.Person,
                title = "Name",
                value = user.name,
            )

            ProfileField(
                icon = Icons.Default.Email,
                title = "Email",
                value = user.email,
            )

            val phoneNumber =
                if (user.phoneNumber.isNullOrBlank()) "not added yet" else user.phoneNumber

            ProfileField(
                icon = Icons.Default.Phone,
                title = "Phone Number",
                value = phoneNumber,
            ) {
                TextButton(
                    onClick = navigateToChangePhoneNumber,
                ) {
                    Text(text = "Change")
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(MaterialTheme.spacing.medium),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                authViewModel.logout()
                navigateToLoginScreen()
            }) {
            Text(text = "Logout")
        }
    }
}

