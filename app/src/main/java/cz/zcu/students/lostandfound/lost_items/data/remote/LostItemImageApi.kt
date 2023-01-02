package cz.zcu.students.lostandfound.lost_items.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import cz.zcu.students.lostandfound.common.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LostItemImageApi(
    storage: FirebaseStorage,
) {
    private val imagesRef = storage.reference.child(Constants.IMAGES_KEY)

    suspend fun addImageToFirebaseStorage(
        imageUri: Uri,
        lostItemId: String,
    ): Uri {
        return withContext(Dispatchers.IO) {
            return@withContext imagesRef
                .child("$lostItemId.jpg")
                .putFile(imageUri)
                .await()
                .storage
                .downloadUrl
                .await()
        }
    }
}