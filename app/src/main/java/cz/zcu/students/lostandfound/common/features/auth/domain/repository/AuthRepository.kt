package cz.zcu.students.lostandfound.common.features.auth.domain.repository

import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.util.Response

/**
 * Authorization repository.
 *
 * @see Response
 */
interface AuthRepository {

    /**
     * Checks current user authentication state.
     *
     * @return `true` if current user is authenticated, `false` otherwise.
     */
    fun isUserAuthenticated(): Boolean

    /** Logouts current user */
    fun logout()

    /**
     * Creates new user.
     *
     * @return `true` if user was successfully created, `false` otherwise.
     */
    suspend fun createNewUser(): Response<Boolean>

    /**
     * Gets currently logged in [User].
     *
     * @return currently logged in [User].
     */
    suspend fun getCurrentUser(): Response<User>

    /**
     * Updates current user based on passed [user] reference.
     *
     * @param user user object to update the currently logged in user.
     * @return `true` if success, `false` otherwise.
     */
    suspend fun updateCurrentUser(user: User): Response<Boolean>

    /**
     * Gets user based on the passed id in [postOwnerId].
     *
     * @param postOwnerId id of user to find.
     * @return [User] with corresponding id.
     */
    suspend fun getUser(postOwnerId: String): Response<User>
}