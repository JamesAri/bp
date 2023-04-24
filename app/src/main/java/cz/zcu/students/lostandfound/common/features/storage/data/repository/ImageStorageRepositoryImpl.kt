package cz.zcu.students.lostandfound.common.features.storage.data.repository

import android.net.Uri
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.common.features.storage.data.remote.ImageStorageApi
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import cz.zcu.students.lostandfound.common.util.Response
import cz.zcu.students.lostandfound.common.util.Response.Error
import cz.zcu.students.lostandfound.common.util.Response.Success
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of image storage repository [ImageStorageRepository].
 *
 * This repository takes advantage of
 * [Firebase Storage](https://firebase.google.com/docs/storage)
 * for saving images.
 *
 * @property api image storage API [ImageStorageApi].
 * @see AuthRepository
 * @see Response
 */
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