package cz.zcu.students.lostandfound.common.features.auth.presentation.login

import android.app.Activity
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val imageRepo: ImageStorageRepository,
) : ViewModel() {

    var currentUser by mutableStateOf<Response<User>>(Success(null))
        private set

    var updateCurrentUserStatus by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    private fun fetchCurrentUser() {
        if (authRepo.isUserAuthenticated()) {
            currentUser = Loading
            viewModelScope.launch {
                currentUser = authRepo.getCurrentUser()
            }
        } else {
            currentUser = Success(null)
        }
    }

    init {
        fetchCurrentUser()
    }

    fun logout() {
        authRepo.logout()
        currentUser = Success(null)
    }

    fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult,
    ) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModelScope.launch {
                if (result.idpResponse?.isNewUser == true)
                    authRepo.createNewUser()
                fetchCurrentUser()
            }
        } else {
            currentUser = Error(Exception("sign in failed, couldn't load user data"))
        }
    }

    private fun updateCurrentUser(user: User) {
        viewModelScope.launch {
            updateCurrentUserStatus = Loading
            val updateStatus = authRepo.updateCurrentUser(user)
            currentUser = authRepo.getCurrentUser()
            updateCurrentUserStatus = updateStatus
        }
    }

    fun updateCurrentUserPhoneNumber(phoneNumber: String?) {
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

    fun updateCurrentUserProfilePicture(uri: Uri) {
        viewModelScope.launch {
            when (val currentUserSnapshotResponse = currentUser) {
                Loading -> updateCurrentUserStatus = Error(Exception("not logged in"))
                is Error -> updateCurrentUserStatus = Error(currentUserSnapshotResponse.error)
                is Success -> {
                    updateCurrentUserStatus = Loading
                    currentUserSnapshotResponse.data?.let {
                        when (val storageUriResponse = imageRepo.addImageToStorage(uri, it.id)) {
                            is Error -> updateCurrentUserStatus = Error(storageUriResponse.error)
                            Loading -> {}
                            is Success -> {
                                if (storageUriResponse.data != null) {
                                    updateCurrentUser(
                                        it.copy(
                                            photoUri = storageUriResponse.data,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    suspend fun getUser(postOwnerId: String): User? {
        return when (val userResponse = authRepo.getUser(postOwnerId)) {
            is Success -> userResponse.data
            else -> null
        }
    }
}