package cz.zcu.students.jetpackcomposetestproject.presentation

import cz.zcu.students.jetpackcomposetestproject.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
