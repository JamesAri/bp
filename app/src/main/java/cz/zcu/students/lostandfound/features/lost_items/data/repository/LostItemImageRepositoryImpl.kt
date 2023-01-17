package cz.zcu.students.lostandfound.features.lost_items.data.repository

import android.net.Uri
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.Error
import cz.zcu.students.lostandfound.common.util.Response.Success
import cz.zcu.students.lostandfound.features.lost_items.data.remote.LostItemImageApi
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LostItemImageRepositoryImpl @Inject constructor(
    private val api: LostItemImageApi
) : LostItemImageRepository {

    override suspend fun addImageToStorage(
        imageUri: Uri,
        lostItemId: String,
    ): Response<Uri> {
        return try {
            val downloadUrl = api.addImageToFirebaseStorage(
                imageUri = imageUri,
                lostItemId = lostItemId
            )
            Success(downloadUrl)
        } catch (e: Exception) {
            Error(e)
        }
    }
}