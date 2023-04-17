package cz.zcu.students.lostandfound.common.features.auth.domain.repository

import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.util.Response

interface AuthRepository {
    fun isUserAuthenticated(): Boolean
    fun logout()
    suspend fun createNewUser(): Response<Boolean>
    suspend fun getCurrentUser(): Response<User>
    suspend fun updateCurrentUser(user: User): Response<Boolean>
    suspend fun getUser(postOwnerId: String): Response<User>
}