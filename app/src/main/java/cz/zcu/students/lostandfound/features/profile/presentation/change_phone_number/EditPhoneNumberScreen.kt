package cz.zcu.students.lostandfound.features.profile.presentation.change_phone_number

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseSnackBarHandler
import cz.zcu.students.lostandfound.common.constants.Validations.Companion.MAX_PHONE_NUMBER_LENGTH
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
    ChangePhoneNumberForm()

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
        onTrueMessage = stringResource(R.string.screen_profile_phone_number_change_success),
        onTrueAction = navigateToProfile,
        onFalseMessage = stringResource(R.string.screen_profile_phone_number_change_failure),
        coroutineScope = coroutineScope,
    )
}

@Composable
fun ChangePhoneNumberForm(
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium)
    ) {
        ChangeControls()
        RemoveButton()
    }
}

@Composable
fun RemoveButton(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    Button(
        onClick = {
            authViewModel.updateCurrentUserPhoneNumber(null)
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Text(text = stringResource(R.string.screen_profile_remove_number))
    }
}

@Composable
fun ChangeControls(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var phoneNumber by remember { mutableStateOf("") }
    var validationState by remember { mutableStateOf(false) }

    val validationMessage = if (!validationState && phoneNumber.isNotEmpty()) {
        stringResource(R.string.screen_profile_validation_message, MAX_PHONE_NUMBER_LENGTH)
    } else {
        ""
    }

    Column {
        TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                validationState = validatePhoneNumber(phoneNumber)
            },
            label = { Text(text = stringResource(R.string.screen_profile_phone_number)) },
            placeholder = { Text(text = stringResource(R.string.screen_profile_enter_phone_number)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = stringResource(R.string.screen_profile_change_phone_number),
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
                text = validationMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
            Button(
                enabled = validationState,
                onClick = {
                    if (validationState) {
                        authViewModel.updateCurrentUserPhoneNumber(
                            phoneNumber = phoneNumber,
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = stringResource(R.string.screen_profile_change_action))
            }
        }
    }
}
