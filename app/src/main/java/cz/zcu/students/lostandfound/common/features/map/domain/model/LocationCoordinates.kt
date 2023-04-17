package cz.zcu.students.lostandfound.common.features.map.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationCoordinates(
    val latitude: Double,
    val longitude: Double,
)
