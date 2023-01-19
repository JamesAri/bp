package cz.zcu.students.lostandfound.features.profile.presentation.edit_phone_number

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.General.Companion.MAX_PHONE_NUMBER_LENGTH
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User
import cz.zcu.students.lostandfound.common.features.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.features.profile.presentation.util.validatePhoneNumber
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope

@Composable
fun EditPhoneNumberScreen(
    coroutineScope: CoroutineScope,
    navigateToProfile: () -> Unit,
) {
    LoadUser()

    EmailChangeListener(
        navigateToProfile = navigateToProfile,
        coroutineScope = coroutineScope,
    )
}

@Composable
fun EmailChangeListener(
    coroutineScope: CoroutineScope,
    navigateToProfile: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    ResponseSnackBarHandler(
        response = authViewModel.updateCurrentUserStatus,
        snackbarHostState = LocalSnackbarHostState.current,
        onTrueMessage = "Successfully changed phone number",
        onTrueAction = navigateToProfile,
        onFalseMessage = "Couldn't change phone number",
        coroutineScope = coroutineScope,
    )
}


@Composable
fun LoadUser(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    ResponseHandler(
        response = viewModel.currentUser,
        snackbarHostState = LocalSnackbarHostState.current
    ) { user ->
        ChangePhoneNumberForm(
            user = user,
        )
    }
}

@Composable
fun ChangePhoneNumberForm(
    user: User,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var validationState by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                validationState = validatePhoneNumber(phoneNumber)
                errorMessage = if (!validationState && phoneNumber.isNotEmpty()) {
                    "Phone number must contain only numbers (max. $MAX_PHONE_NUMBER_LENGTH digits)"
                } else {
                    ""
                }
            },
            label = { Text(text = "Phone number") },
            placeholder = { Text(text = "Enter phone number") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "change phone number",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(74.dp)
        ) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
            Button(
                enabled = validationState,
                onClick = {
                    if (validationState) {
                        authViewModel.updateCurrentUser(
                            user = user.copy(
                                phoneNumber = phoneNumber,
                            )
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = "Change")
            }
        }
    }
}

