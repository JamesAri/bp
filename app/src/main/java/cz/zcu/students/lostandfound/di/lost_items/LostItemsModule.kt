package cz.zcu.students.lostandfound.di.lost_items

import com.google.firebase.firestore.FirebaseFirestore
import cz.zcu.students.lostandfound.features.lost_items.data.remote.LostItemApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Lost items API module */
@Module
@InstallIn(SingletonComponent::class)
object LostItemsModule {
    @Provides
    @Singleton
    fun provideLostItemApi(
        db: FirebaseFirestore,
    ): LostItemApi = LostItemApi(db)
}
