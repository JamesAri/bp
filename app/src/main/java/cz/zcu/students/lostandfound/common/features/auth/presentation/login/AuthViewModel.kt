package cz.zcu.students.lostandfound.common.features.auth.presentation.login

import android.app.Activity
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Viewmodel for authentication UI.
 *
 * @property authRepo authorization repository [AuthRepository].
 * @property imageRepo image repository [ImageStorageRepository].
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val imageRepo: ImageStorageRepository,
) : ViewModel() {

    /** State of currently logged in user. */
    var currentUser by mutableStateOf<Response<User>>(Success(null))
        private set

    /** State when updating currently logged in user. */
    var updateCurrentUserStatus by mutableStateOf<Response<Boolean>>(Success(null))
        private set

    /** Fetches current user updating [currentUser]. */
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

    /** Loads currently logged in user when created. */
    init {
        fetchCurrentUser()
    }

    /** Logouts currently logged in user [currentUser]. */
    fun logout() {
        authRepo.logout()
        currentUser = Success(null)
    }

    /**
     * Callback after user logs in / registers that handles db and auth state
     * synchronization.
     *
     * Callback updates [currentUser].
     *
     * @param result [FirebaseAuthUIAuthenticationResult] result.
     */
    fun onSignInResult(
        result: FirebaseAuthUIAuthenticationResult, // TODO: remove Firebase deps. from presentation
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

    /**
     * Updates currently logged in user with [user] reference and sets update
     * status in [updateCurrentUserStatus].
     *
     * @param user [User] to update current user with.
     */
    private fun updateCurrentUser(user: User) {
        viewModelScope.launch {
            updateCurrentUserStatus = Loading
            val updateStatus = authRepo.updateCurrentUser(user)
            currentUser = authRepo.getCurrentUser()
            updateCurrentUserStatus = updateStatus
        }
    }

    /**
     * Updates current user's phone number and sets the result in
     * [updateCurrentUserStatus].
     *
     * @param phoneNumber phone number to set for current user.
     */
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

    /**
     * Updates current user's profile picture and sets the result in
     * [updateCurrentUserStatus].
     *
     * @param uri uri with local image.
     */
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

    /**
     * Gets user based on the id passed in [id].
     *
     * @param id id of the user we are looking for.
     * @return [User] if found, `null` otherwise.
     */
    suspend fun getUser(id: String): User? {
        return when (val userResponse = authRepo.getUser(id)) {
            is Success -> userResponse.data
            else -> null
        }
    }
}