package cz.zcu.students.lostandfound.features.profile.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.common.auth.domain.user.User
import cz.zcu.students.lostandfound.common.auth.presentation.ResponseHandler
import cz.zcu.students.lostandfound.common.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun ProfileScreen(
    navigateToLoginScreen: () -> Unit,
) {
    LoadUser(navigateToLoginScreen = navigateToLoginScreen)
}

@Composable
fun LoadUser(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {
    ResponseHandler(
        response = viewModel.currentUser,
    ) { user ->
        DisplayUserData(
            user = user,
            navigateToLoginScreen = navigateToLoginScreen,
        )
    }
}

@Composable
fun DisplayUserData(
    user: User,
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
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
            ProfileImageIcon(
                photoUri = user.photoUri
            )

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
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(MaterialTheme.spacing.medium),
            shape = MaterialTheme.shapes.medium,
            onClick = {
                viewModel.logout()
                navigateToLoginScreen()
            }) {
            Text(text = "Logout")
        }
    }
}
