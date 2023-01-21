package cz.zcu.students.lostandfound.di.lost_items

import cz.zcu.students.lostandfound.features.lost_items.data.repository.LostItemRepositoryImpl
import cz.zcu.students.lostandfound.features.lost_items.domain.repository.LostItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LostItemsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLostItemRepository(
        lostItemRepositoryImpl: LostItemRepositoryImpl,
    ): LostItemRepository
}