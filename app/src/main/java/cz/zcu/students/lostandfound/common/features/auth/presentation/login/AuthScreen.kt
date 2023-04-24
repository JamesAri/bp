package cz.zcu.students.lostandfound.common.features.auth.presentation.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ProgressBar
import cz.zcu.students.lostandfound.common.util.Response


/** Different options for registration/login from Firebase Auth. */
private val PROVIDERS = listOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

/** Firebase Auth auth process intent. */
private val SIGN_IN_INTENT = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(PROVIDERS)
    .setLogo(R.drawable.login_logo)
    .setTheme(R.style.Theme_App_Login)
    .build()


/**
 * Authentication screen.
 *
 * Contains login and register forms.
 *
 * @param authViewModel authentication viewmodel.
 * @param navigateToMainScreen navigates to home screen of the app.
 */
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit,
) {
    val signInLauncher = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        authViewModel.onSignInResult(result)
        Log.d("AuthScreen", "auth status: $result")
    }

    when (val currentUser = authViewModel.currentUser) {
        Response.Loading -> ProgressBar()
        is Response.Error -> LaunchedEffect(currentUser) {
            signInLauncher.launch(SIGN_IN_INTENT)
            Log.e("AuthScreen", "Error: ${currentUser.error}")
        }
        is Response.Success -> LaunchedEffect(currentUser) {
            if (currentUser.data == null) {
                signInLauncher.launch(SIGN_IN_INTENT)
            } else {
                navigateToMainScreen()
            }
        }
    }
}


