package cz.zcu.students.lostandfound.di

import cz.zcu.students.lostandfound.data.remote.PostApi
import cz.zcu.students.lostandfound.data.remote.PostDto
import cz.zcu.students.lostandfound.data.remote.PostsDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostApi(): PostApi = object : PostApi {
        // TODO: Use FireStore api
        override suspend fun getDtoPosts(): PostsDto {
            TODO("Not yet implemented")
        }

        override suspend fun getDtoPost(id: Int): PostDto {
            TODO("Not yet implemented")
        }
    }
}
