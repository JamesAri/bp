package cz.zcu.students.lostandfound.lost_items.di

import cz.zcu.students.lostandfound.lost_items.data.repository.LostItemRepositoryImpl
import cz.zcu.students.lostandfound.lost_items.domain.repository.LostItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPostRepository(
        postRepositoryImpl: LostItemRepositoryImpl,
    ): LostItemRepository
}