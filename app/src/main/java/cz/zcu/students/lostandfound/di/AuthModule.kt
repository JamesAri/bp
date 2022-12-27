package cz.zcu.students.lostandfound.di

import com.google.firebase.auth.FirebaseAuth
import cz.zcu.students.lostandfound.auth.domain.repository.AuthRepository
import cz.zcu.students.lostandfound.auth.domain.user.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

//    @Provides
//    @Singleton
//    fun provideCurrentUser(
//        authRepository: AuthRepository
//    ): User? = authRepository.getCurrentUser()
}