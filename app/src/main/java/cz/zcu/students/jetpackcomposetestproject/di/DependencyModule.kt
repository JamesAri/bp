package cz.zcu.students.jetpackcomposetestproject.di

import android.app.Application
import android.content.Context
import cz.zcu.students.jetpackcomposetestproject.data.remote.DependencyApi
import cz.zcu.students.jetpackcomposetestproject.data.repository.DependencyRepository
import cz.zcu.students.jetpackcomposetestproject.domain.repository.MyRepository
import cz.zcu.students.jetpackcomposetestproject.ui.viewmodel.DependencyViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module // We create modules based on the use-case
@InstallIn(SingletonComponent::class) // Determines how long do these injected objects live
object DependencyModule {

    @Singleton // How many instances will be created, in this case it's just static factory
    @Provides // We tel Hilt that this is provider method
    fun provideDependencyApi(
        @ApplicationContext context: Context
    ): DependencyApi = DependencyApi(context = context)

    @Singleton
    @Provides
    fun provideRepository(
        dependencyApi: DependencyApi, // Hilt will deal with this Injected object defined above
        myRepository: MyRepository, // Interface bound with specific implementation, see RepositoryModule
        @Named("StringOne") stringOne: String,
        @Named("StringTwo") stringTwo: String,
        app: Application,
    ): DependencyRepository = DependencyRepository(
        dependencyApi = dependencyApi,
        myRepository = myRepository,
        stringOne = stringOne,
        stringTwo = stringTwo,
        app = app,
    )

    @Singleton
    @Provides
    @Named("StringOne")
    fun provideStringOne(): String = "StringOne"

    @Singleton
    @Provides
    @Named("StringTwo")
    fun provideStringTwo(): String = "StringTwo"
}