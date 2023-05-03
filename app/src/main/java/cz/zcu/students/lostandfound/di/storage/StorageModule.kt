package cz.zcu.students.lostandfound.di.storage

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import cz.zcu.students.lostandfound.common.features.storage.data.remote.ImageStorageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Storage module
 *
 * Provides Firebase storage and image storage API
 */
@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideImageStorageApi(
        storage: FirebaseStorage,
    ): ImageStorageApi = ImageStorageApi(storage)
}