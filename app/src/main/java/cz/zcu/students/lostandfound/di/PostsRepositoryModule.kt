package cz.zcu.students.lostandfound.di

import cz.zcu.students.lostandfound.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PostsRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPostsRepository(
        postsRepositoryModule: PostsRepositoryModule,
    ): PostRepository
}