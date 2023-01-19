package cz.zcu.students.lostandfound.common.features.auth.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import cz.zcu.students.lostandfound.common.constants.General
import cz.zcu.students.lostandfound.common.features.auth.data.remote.dto.DbUserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserApi @Inject constructor(
    db: FirebaseFirestore,
) {
    private val collectionRef = db.collection(General.USER_COLLECTION_KEY)

    suspend fun getUser(id: String): DbUserDto? {
        return withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(id)
                .get()
                .await()
                .toObject(DbUserDto::class.java)
        }
    }

    suspend fun updateDbUser(user: DbUserDto) {
        withContext(Dispatchers.IO) {
            return@withContext collectionRef
                .document(user.id)
                .set(user)
                .await()
        }
    }
}