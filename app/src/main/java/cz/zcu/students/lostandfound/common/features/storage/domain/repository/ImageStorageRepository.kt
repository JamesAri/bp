package cz.zcu.students.lostandfound.common.features.storage.domain.repository

import android.net.Uri
import cz.zcu.students.lostandfound.common.util.Response

interface ImageStorageRepository {
    suspend fun addImageToStorage(imageUri: Uri, name: String): Response<Uri>
}
