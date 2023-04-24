package cz.zcu.students.lostandfound.common.features.storage.domain.repository

import android.net.Uri
import cz.zcu.students.lostandfound.common.util.Response

/**
 * Image storage repository.
 *
 * @see Response
 */
interface ImageStorageRepository {

    /**
     * Adds image to the storage.
     *
     * @param imageUri the source of the upload.
     * @param name filename of the image.
     * @return
     */
    suspend fun addImageToStorage(imageUri: Uri, name: String): Response<Uri>
}
