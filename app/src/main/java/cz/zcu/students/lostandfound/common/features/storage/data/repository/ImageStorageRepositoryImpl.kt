package cz.zcu.students.lostandfound.common.features.storage.data.repository

import android.net.Uri
import cz.zcu.students.lostandfound.common.features.storage.data.remote.ImageStorageApi
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStorageRepositoryImpl @Inject constructor(
    private val api: ImageStorageApi
) : ImageStorageRepository {

    override suspend fun addImageToStorage(
        imageUri: Uri,
        name: String,
    ): Response<Uri> {
        return try {
            val downloadUrl = api.addImageToFirebaseStorage(
                imageUri = imageUri,
                name = name
            )
            Success(downloadUrl)
        } catch (e: Exception) {
            Error(e)
        }
    }
}