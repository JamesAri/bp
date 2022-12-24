package cz.zcu.students.lostandfound.lost_items.di

import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemApi
import cz.zcu.students.lostandfound.lost_items.data.remote.LostItemApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {
    @Binds
    @Singleton
    abstract fun bindLostItemApi(
        postApiImpl: LostItemApiImpl,
    ): LostItemApi
}