package cz.zcu.students.lostandfound.common.features.auth.presentation.login

import android.app.Activity
import android.util.Log
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
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
) : ViewModel() {

    var currentUser by mutableStateOf<Response<User>>(Success(null))
        private set

    var updateCurrentUserStatus by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    private fun fetchCurrentUser() {
        if (repo.isUserAuthenticated()) {
            currentUser = Loading
            viewModelScope.launch {
                currentUser = repo.getCurrentUser()
            }
        } else {
            currentUser = Success(null)
        }
    }

    init {
        fetchCurrentUser()
    }

    fun logout() {
        repo.logout()
        currentUser = Success(null)
    }

    private fun updateCurrentUser(user: User) {
        viewModelScope.launch {
            updateCurrentUserStatus = Loading
            val updateStatus = repo.updateCurrentUser(user)
            currentUser = repo.getCurrentUser()
            updateCurrentUserStatus = updateStatus
        }
    }

    fun updateCurrentUserPhoneNumber(phoneNumber: String) {
        when (val currentUserSnapshot = currentUser) {
            Loading -> updateCurrentUserStatus = Error(Exception("not logged in"))
            is Error -> updateCurrentUserStatus = Error(currentUserSnapshot.error)
            is Success -> {
                currentUserSnapshot.data?.let {
                    updateCurrentUser(
                        it.copy(
                            phoneNumber = phoneNumber,
                        )
                    )
                }
            }
        }
    }

    fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult,
    ) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModelScope.launch {
                if (result.idpResponse?.isNewUser == true)
                    repo.createNewUser()
                fetchCurrentUser()
            }
        } else {
            currentUser = Error(Exception("sign in failed, couldn't load user data"))
        }
    }
}