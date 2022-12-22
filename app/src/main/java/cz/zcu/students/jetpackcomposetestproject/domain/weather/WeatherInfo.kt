package cz.zcu.students.jetpackcomposetestproject.domain.weather


data class WeatherInfo(
    /** **`DayFromToday:WeatherData`**
     *
     * Examples:
     *  - `0:WeatherData` means today's [WeatherData]
     *  - `1:WeatherData` means tomorrow's [WeatherData]
     */
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?
)
