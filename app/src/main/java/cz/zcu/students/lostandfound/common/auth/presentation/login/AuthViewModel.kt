package cz.zcu.students.lostandfound.common.auth.presentation.login

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import cz.zcu.students.lostandfound.common.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.auth.domain.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val auth: AuthRepository,
) : ViewModel() {

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            auth.logout()
        }
    }

    var currentUser by mutableStateOf<User?>(null)
        private set

    fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = result.idpResponse
            currentUser = if (result.resultCode == Activity.RESULT_OK) {
                auth.getCurrentUser()
            } else {
                null
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}