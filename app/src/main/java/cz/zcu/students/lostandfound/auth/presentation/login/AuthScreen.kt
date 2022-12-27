package cz.zcu.students.lostandfound.auth.presentation.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract


val PROVIDERS = listOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

val SIGN_IN_INTENT = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(PROVIDERS)
    .build()


@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit
) {
    val signInLauncher = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        viewModel.onSignInResult(result)
        Log.d("AuthActivity", "onCreate: $result")
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


