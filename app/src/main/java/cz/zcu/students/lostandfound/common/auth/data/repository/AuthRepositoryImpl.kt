package cz.zcu.students.lostandfound.common.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import cz.zcu.students.lostandfound.common.auth.data.mappers.toUser
import cz.zcu.students.lostandfound.common.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.auth.domain.user.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
) : AuthRepository {
    override suspend fun getCurrentUser(): User? {
        return FirebaseAuth.getInstance().currentUser?.toUser()
    }

    override suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}