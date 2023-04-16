package cz.zcu.students.lostandfound.common.features.map.domain.location_coordinates

import kotlinx.serialization.Serializable

@Serializable
data class LocationCoordinates(
    val latitude: Double,
    val longitude: Double,
)
