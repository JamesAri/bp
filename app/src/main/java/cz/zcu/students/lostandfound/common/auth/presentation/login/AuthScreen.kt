package cz.zcu.students.lostandfound.common.auth.presentation.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import cz.zcu.students.lostandfound.R


val PROVIDERS = listOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

val SIGN_IN_INTENT = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(PROVIDERS)
    .setLogo(R.drawable.login_logo)
    .setTheme(R.style.Theme_App_Login)
    .build()


@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit,
) {
    val signInLauncher = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        viewModel.onSignInResult(result)
        Log.d("AuthScreen", "onCreate: $result")
    }

    val currentUser = viewModel.currentUser

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            signInLauncher.launch(SIGN_IN_INTENT)
        } else {
            navigateToMainScreen()
        }
    }
}


