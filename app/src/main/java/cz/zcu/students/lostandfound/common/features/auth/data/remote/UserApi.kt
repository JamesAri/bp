package cz.zcu.students.lostandfound.common.features.auth.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.USER_COLLECTION_KEY
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.DbUserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * API for Firestore user manipulation.
 *
 * @param db reference to Firestore [FirebaseFirestore] database.
 * @constructor constructs user API with Firestore [FirebaseFirestore]
 *     database reference.
 */
class UserApi @Inject constructor(
    db: FirebaseFirestore,
) {
    /** Collection reference with users */
    private val collectionRef = db.collection(USER_COLLECTION_KEY)

    /**
     * Gets user from Firestore database based on the passed [id].
     *
     * @param id id of user.
     * @return Firestore DTO user [DbUserDto].
     */
    suspend fun getUser(id: String): DbUserDto? {
        return withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(id)
                .get()
                .await()
                .toObject(DbUserDto::class.java)
        }
    }

    /**
     * Updates user from Firestore database based on the passed [user]
     * reference.
     *
     * @param user Firestore DTO user [DbUserDto].
     */
    suspend fun updateDbUser(user: DbUserDto) {
        withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(user.id)
                .set(user)
                .await()
        }
    }
}