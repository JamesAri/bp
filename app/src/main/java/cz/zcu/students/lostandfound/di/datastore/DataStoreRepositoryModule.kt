package cz.zcu.students.lostandfound.di.datastore

import cz.zcu.students.lostandfound.features.settings.data.repository.AppSettingsRepositoryImpl
import cz.zcu.students.lostandfound.features.settings.domain.repository.AppSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** App settings repository module */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAppSettingsRepository(
        appSettingsRepositoryImpl: AppSettingsRepositoryImpl,
    ): AppSettingsRepository
}