package cz.zcu.students.lostandfound.common.features.map.presentation.util

import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates

/**
 * Extension function for centering [CameraPositionState] based on
 * [LocationCoordinates].
 *
 * @param location location to center on.
 */
suspend fun CameraPositionState.centerOnLocation(
    location: LocationCoordinates
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)

/**
 * Maps [Location] to [LocationCoordinates].
 *
 * @return [LocationCoordinates] location.
 */
fun Location.toLocationCoordinates(): LocationCoordinates {
    return LocationCoordinates(this.latitude, this.longitude)
}

/**
 * Maps [LatLng] to [LocationCoordinates].
 *
 * @return [LocationCoordinates] location.
 */
fun LatLng.toLocationCoordinates(): LocationCoordinates {
    return LocationCoordinates(this.latitude, this.longitude)
}

/**
 * Maps [LocationCoordinates] to [LatLng].
 *
 * @return [LatLng] location.
 */
fun LocationCoordinates.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}
