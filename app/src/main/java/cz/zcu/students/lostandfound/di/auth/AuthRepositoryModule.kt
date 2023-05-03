package cz.zcu.students.lostandfound.di.auth

import cz.zcu.students.lostandfound.common.features.auth.data.repository.AuthRepositoryImpl
import cz.zcu.students.lostandfound.common.features.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Authentication repository module */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository
}