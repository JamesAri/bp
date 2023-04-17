package cz.zcu.students.lostandfound.common.features.map.presentation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.common.features.map.presentation.util.toLocationCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    var lastKnownLocation by mutableStateOf<LocationCoordinates?>(null)
        private set

    var searchLocation by mutableStateOf<LocationCoordinates?>(null)

    fun getLatitudeFromName(context: Context, locale: Locale?, text: String) {
        if (text.isEmpty()) return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val geocoder = if (locale != null) Geocoder(context, locale) else Geocoder(context)
                if (Build.VERSION.SDK_INT >= 33) {
                    val geocodeListener = Geocoder.GeocodeListener { addresses ->
                        // do something with the addresses list
                        if (addresses.isNotEmpty()) {
                            val address: Address = addresses[0]
                            searchLocation = LocationCoordinates(address.latitude, address.longitude)
                        }
                    }
                    // declare here the geocodeListener, as it requires Android API 33
                    geocoder.getFromLocationName(text, 1, geocodeListener)
                } else {
                    // For Android SDK < 33, the addresses list will be still obtained from the getFromLocation() method
                    val addresses = geocoder.getFromLocationName(text, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val address: Address = addresses[0]
                        searchLocation = LocationCoordinates(address.latitude, address.longitude)
                    }
                }
            }
        }
    }

    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location = task.result
                    lastKnownLocation = location.toLocationCoordinates()
                }
            }
        } catch (e: SecurityException) {
            Log.e("MapViewModel", "getDeviceLocation: ${e.message}")
        }
    }

}