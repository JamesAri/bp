package cz.zcu.students.jetpackcomposetestproject.di

import cz.zcu.students.jetpackcomposetestproject.data.location.DefaultLocationTracker
import cz.zcu.students.jetpackcomposetestproject.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherLocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        defaultLocationTracker: DefaultLocationTracker
    ): LocationTracker
}