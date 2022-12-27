package cz.zcu.students.lostandfound.auth.presentation.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import cz.zcu.students.lostandfound.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


val PROVIDERS = listOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

val SIGN_IN_INTENT = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(PROVIDERS)
    .setLogo(R.drawable.login_logo)
    .setTheme(R.style.LoginTheme)
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


