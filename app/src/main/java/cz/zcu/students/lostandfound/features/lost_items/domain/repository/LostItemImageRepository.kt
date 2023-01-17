package cz.zcu.students.lostandfound.features.lost_items.domain.repository

import android.net.Uri
import cz.zcu.students.lostandfound.common.util.Response

interface LostItemImageRepository {
    suspend fun addImageToStorage(imageUri: Uri, lostItemId: String): Response<Uri>
}