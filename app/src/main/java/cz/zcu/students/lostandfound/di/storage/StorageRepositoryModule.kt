package cz.zcu.students.lostandfound.di.storage

import cz.zcu.students.lostandfound.common.features.storage.data.repository.ImageStorageRepositoryImpl
import cz.zcu.students.lostandfound.common.features.storage.domain.repository.ImageStorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindImageStorageRepository(
        imageStorageRepository: ImageStorageRepositoryImpl,
    ): ImageStorageRepository
}