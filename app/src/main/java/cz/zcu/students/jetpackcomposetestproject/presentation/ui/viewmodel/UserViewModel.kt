package cz.zcu.students.jetpackcomposetestproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

data class User(
    val id: String,
    val isOnline: Boolean,
    val userName: String,
    val email: String,
)

class UserViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(true)

    private val _users = MutableStateFlow<List<User>>(listOf())
    val users = _users.asStateFlow()

    val onlineUsers = combine(_isLoggedIn, _users) { isLoggedIn, users ->
        if (isLoggedIn) {
            users.filter { user -> // using _users.value might lead to race conditions
                user.isOnline
            }
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val localUser = users.map { users ->
        users.find { it.id == "local" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun onUserJoined(user: User) {
        _users.update { it + user }
    }

    fun onUserInfoUpdated(user: User) {
        _users.update {
            it.map { curUser ->
                if (curUser.id == user.id) user else curUser
            }
        }
    }
}