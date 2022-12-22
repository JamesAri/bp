package cz.zcu.students.jetpackcomposetestproject.di

import cz.zcu.students.jetpackcomposetestproject.data.repository.MyRepositoryImpl
import cz.zcu.students.jetpackcomposetestproject.data.repository.WeatherRepositoryImpl
import cz.zcu.students.jetpackcomposetestproject.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl,
    ): WeatherRepository
}