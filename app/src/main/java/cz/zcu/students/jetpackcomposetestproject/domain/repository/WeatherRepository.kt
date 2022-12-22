package cz.zcu.students.jetpackcomposetestproject.domain.repository

import cz.zcu.students.jetpackcomposetestproject.domain.util.Resource
import cz.zcu.students.jetpackcomposetestproject.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}