package cz.zcu.students.jetpackcomposetestproject.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}