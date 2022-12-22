package cz.zcu.students.jetpackcomposetestproject.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.jetpackcomposetestproject.domain.location.LocationTracker
import cz.zcu.students.jetpackcomposetestproject.domain.repository.WeatherRepository
import cz.zcu.students.jetpackcomposetestproject.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
) : ViewModel() {
    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getWeatherData(location.latitude, location.latitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message,
                        )
                    }
                }
            } ?: kotlin.run {
                when (val result =
                    repository.getWeatherData(49.738430,13.373637)) { // Pilsen - for emulators
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null,
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message,
                        )
                    }
                }
//                state = state.copy(
//                    isLoading = false,
//                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
//                )
            }
        }
    }
}