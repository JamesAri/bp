package cz.zcu.students.lostandfound.di.auth

import com.google.firebase.firestore.FirebaseFirestore
import cz.zcu.students.lostandfound.common.auth.data.remote.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserApi(
        db: FirebaseFirestore,
    ): UserApi = UserApi(db)
}