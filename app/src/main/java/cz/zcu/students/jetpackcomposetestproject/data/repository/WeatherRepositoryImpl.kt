package cz.zcu.students.jetpackcomposetestproject.data.repository

import cz.zcu.students.jetpackcomposetestproject.data.mappers.toWeatherInfo
import cz.zcu.students.jetpackcomposetestproject.data.remote.WeatherApi
import cz.zcu.students.jetpackcomposetestproject.domain.repository.WeatherRepository
import cz.zcu.students.jetpackcomposetestproject.domain.util.Resource
import cz.zcu.students.jetpackcomposetestproject.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long,
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}