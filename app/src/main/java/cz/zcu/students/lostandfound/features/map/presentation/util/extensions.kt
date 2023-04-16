package cz.zcu.students.lostandfound.features.map.presentation.util

import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import cz.zcu.students.lostandfound.common.features.location.LocationCoordinates

suspend fun CameraPositionState.centerOnLocation(
    location: LocationCoordinates
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)

fun Location.toLostItemLocation(): LocationCoordinates {
    return LocationCoordinates(this.latitude, this.longitude)
}

fun LatLng.toLostItemLocation(): LocationCoordinates {
    return LocationCoordinates(this.latitude, this.longitude)
}

