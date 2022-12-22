package cz.zcu.students.jetpackcomposetestproject.data.remote

import com.squareup.moshi.Json

data class WeatherDataDto(
    val time: List<String>, // no need to annotate, it has the same name as from the request
    @field:Json(name="temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name="weathercode")
    val weatherCodes: List<Int>,
    @field:Json(name="pressure_msl")
    val pressures: List<Double>,
    @field:Json(name="windspeed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name="relativehumidity_2m")
    val humidities: List<Double>,
)
