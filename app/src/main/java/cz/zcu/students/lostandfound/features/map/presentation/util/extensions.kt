package cz.zcu.students.lostandfound.features.map.presentation.util

import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import cz.zcu.students.lostandfound.features.map.domain.LostItemLocation

suspend fun CameraPositionState.centerOnLocation(
    location: LostItemLocation
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)

fun Location.toLostItemLocation(): LostItemLocation {
    return LostItemLocation(this.latitude, this.longitude)
}

