package cz.zcu.students.lostandfound.common.auth.domain.repository

import cz.zcu.students.lostandfound.common.auth.domain.user.User
import cz.zcu.students.lostandfound.common.util.Response

interface AuthRepository {
    fun isUserAuthenticated(): Boolean
    fun logout()
    suspend fun getCurrentUser(): Response<User>
    suspend fun updateCurrentUser(user: User): Response<Boolean>
}