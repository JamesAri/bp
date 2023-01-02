package cz.zcu.students.lostandfound.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemApi
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LostItemsModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseStorage() = Firebase.storage

    @Provides
    @Singleton
    fun provideLostItemApi(
        db: FirebaseFirestore,
    ): LostItemApi = LostItemApi(db)

    @Provides
    @Singleton
    fun provideLostItemImageApi(
        storage: FirebaseStorage,
    ): LostItemImageApi = LostItemImageApi(storage)


}
