package cz.zcu.students.lostandfound.common.features.storage.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import cz.zcu.students.lostandfound.common.constants.Firebase.Companion.IMAGES_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * API for Firebase Storage image manipulation.
 *
 * @param storage reference to Firebase [FirebaseStorage] storage.
 * @constructor constructs storage image API with Firebase
 *     [FirebaseStorage] storage reference.
 */
class ImageStorageApi(
    storage: FirebaseStorage,
) {
    /** Storage reference with images. */
    private val imagesRef = storage.reference.child(IMAGES_KEY)

    /**
     * Adds image to Firebase Storage.
     *
     * @param imageUri the source of the upload.
     * @param name filename of the image.
     * @return remote public [Uri] with stored image.
     */
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