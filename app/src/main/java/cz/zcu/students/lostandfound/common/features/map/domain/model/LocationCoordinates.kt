package cz.zcu.students.lostandfound.common.features.map.domain.model

import kotlinx.serialization.Serializable

/**
 * Domain layer location coordinates representation.
 *
 * @property latitude latitude distance.
 * @property longitude longitude distance.
 */
@Serializable
data class LocationCoordinates(
    val latitude: Double,
    val longitude: Double,
)
