package cz.zcu.students.lostandfound.common.features.auth.presentation.login

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
) : ViewModel() {

    var authenticatedState by mutableStateOf(repo.isUserAuthenticated())
        private set

    var currentUser by mutableStateOf<Response<User>>(Loading)
        private set

    var updateCurrentUserStatus by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    private fun fetchCurrentUser() {
        authenticatedState = repo.isUserAuthenticated()
        viewModelScope.launch {
            currentUser = repo.getCurrentUser()
        }
    }

    init {
        fetchCurrentUser()
    }

    fun logout() {
        repo.logout()
        fetchCurrentUser()
    }

    fun updateCurrentUser(user: User) {
        viewModelScope.launch {
            updateCurrentUserStatus = Loading
            val updateStatus = repo.updateCurrentUser(user)
            currentUser = repo.getCurrentUser()
            updateCurrentUserStatus = updateStatus
        }
    }

    fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult,
    ) {
        if (result.resultCode == Activity.RESULT_OK) {
            fetchCurrentUser()
        } else {
            currentUser = Error(Exception("sign in failed, couldn't load user data"))
        }
    }
}