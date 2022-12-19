package cz.zcu.students.jetpackcomposetestproject.di

import cz.zcu.students.jetpackcomposetestproject.data.repository.MyRepositoryImpl
import cz.zcu.students.jetpackcomposetestproject.domain.repository.MyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Injecting interface demo
// Why do this?
// - Hilt generates less code then creating instances in module with @Provides annotation

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // Makes sure that MyRepositoryImpl implementation is chosen when someone asks for
    // MyRepository abstraction.
    @Binds
    @Singleton
    abstract fun bindMyRepository(
        myRepositoryImpl: MyRepositoryImpl
    ) : MyRepository
}