package cz.zcu.students.jetpackcomposetestproject.data.remote

import com.squareup.moshi.Json

// using moshi for json parsing
data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)
