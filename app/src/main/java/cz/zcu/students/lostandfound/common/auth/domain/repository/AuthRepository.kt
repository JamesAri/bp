package cz.zcu.students.lostandfound.common.auth.domain.repository

import cz.zcu.students.lostandfound.common.auth.domain.user.User

interface AuthRepository {
    suspend fun getCurrentUser(): User?
    suspend fun logout()
}