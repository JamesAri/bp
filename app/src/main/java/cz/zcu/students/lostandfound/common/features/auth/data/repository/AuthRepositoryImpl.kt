package cz.zcu.students.lostandfound.common.features.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import cz.zcu.students.lostandfound.common.features.auth.data.mappers.toDbUserDto
import cz.zcu.students.lostandfound.common.features.auth.data.mappers.toUser
import cz.zcu.students.lostandfound.common.features.auth.data.remote.UserApi
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.CurrentUserDto
import cz.zcu.students.lostandfound.common.features.auth.data.util.getRandomProfileAvatarUri
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.storage.data.remote.ImageStorageApi
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.Error
import cz.zcu.students.lostandfound.common.util.Response.Success
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of authorization repository [AuthRepository].
 *
 * This repository takes advantage of
 * [Firebase Authentication](https://firebase.google.com/docs/auth)
 * for authentication and
 * [Firebase Storage](https://firebase.google.com/docs/storage)
 * for saving images.
 *
 * @property userApi user API [UserApi].
 * @property imageStorageApi image storage API [ImageStorageApi].
 * @see AuthRepository
 * @see Response
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val imageStorageApi: ImageStorageApi,
) : AuthRepository {

    /** Firebase Auth reference. */
    private val authInstance = FirebaseAuth.getInstance()

    override fun isUserAuthenticated(): Boolean {
        return authInstance.currentUser != null
    }

    override fun logout() {
        authInstance.signOut()
    }

    override suspend fun createNewUser(): Response<Boolean> {
        return try {
            val authUser = authInstance.currentUser ?: throw Exception("not logged in")
            val dbUserDto = authUser.toDbUserDto()
            val randomAvatarUri = getRandomProfileAvatarUri()
            // TODO (feature) - save just index and not the whole image
            val storageUri = imageStorageApi.addImageToFirebaseStorage(
                imageUri = randomAvatarUri,
                name = dbUserDto.id
            )
            dbUserDto.photoUri = storageUri.toString()
            userApi.updateDbUser(dbUserDto)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getCurrentUser(): Response<User> {
        return try {
            val authUser = authInstance.currentUser ?: throw Exception("not logged in")
            val dbUser =
                userApi.getUser(authUser.uid) ?: throw Exception("missing user db reference")
            val user = CurrentUserDto(dbUser = dbUser, authUser = authUser).toUser()
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
            userApi.updateDbUser(dbUserDto)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getUser(postOwnerId: String): Response<User> {
        return try {
            val dbUser = userApi.getUser(postOwnerId)
            val user = dbUser?.toUser()
            Success(user)
        } catch (e: Exception) {
            Error(e)
        }
    }
}