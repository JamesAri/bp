package cz.zcu.students.lostandfound.common.features.storage.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.IMAGES_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImageStorageApi(
    storage: FirebaseStorage,
) {
    private val imagesRef = storage.reference.child(IMAGES_KEY)

    suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
        name: String,
    ): Uri {
        return withContext(Dispatchers.IO) {
            return@withContext imagesRef
                .child("$name.jpg")
                .putFile(imageUri)
                .await()
                .storage
                .downloadUrl
                .await()
        }
    }
}