package cz.zcu.students.lostandfound.common.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import cz.zcu.students.lostandfound.common.auth.data.mappers.toDbUserDto
import cz.zcu.students.lostandfound.common.auth.data.mappers.toUser
import cz.zcu.students.lostandfound.common.auth.data.remote.UserApi
import cz.zcu.students.lostandfound.common.auth.data.remote.dto.DbUserDto
import cz.zcu.students.lostandfound.common.auth.data.remote.dto.UserDto
import cz.zcu.students.lostandfound.common.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.auth.domain.user.User
import cz.zcu.students.lostandfound.common.extensions.isNotNull
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.Success
import cz.zcu.students.lostandfound.common.util.Response.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: UserApi,
) : AuthRepository {

    private val authInstance = FirebaseAuth.getInstance()

    override fun isUserAuthenticated(): Boolean {
        return authInstance.currentUser != null
    }

    override fun logout() {
        authInstance.signOut()
    }

    override suspend fun getCurrentUser(): Response<User> {
        return try {
            // authUser is local, no need for async call with dbUser
            val authUser = authInstance.currentUser ?: return Success(null)
            val dbUser = api.getUser(authUser.uid)
            val user = UserDto(dbUser = dbUser, authUser = authUser).toUser()
            Success(user)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun updateCurrentUser(user: User): Response<Boolean> {
        return try {
            val authUser = authInstance.currentUser ?: throw Exception("not logged in")
            val dbUserDto = user.toDbUserDto()
            if (authUser.uid != dbUserDto.id) throw Exception("access denied, cannot update user")
            api.updateDbUser(dbUserDto)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }


}