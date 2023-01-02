package cz.zcu.students.lostandfound.di

import cz.zcu.students.lostandfound.lost_items.data.repository.LostItemImageRepositoryImpl
import cz.zcu.students.lostandfound.lost_items.data.repository.LostItemRepositoryImpl
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemImageRepository
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemRepository
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
    abstract fun bindPostRepository(
        lostItemRepositoryImpl: LostItemRepositoryImpl,
    ): LostItemRepository

    @Binds
    @Singleton
    abstract fun bindLostItemImageRepository(
        lostItemImageRepositoryImpl: LostItemImageRepositoryImpl,
    ): LostItemImageRepository

}