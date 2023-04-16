package cz.zcu.students.lostandfound.common.features.map.presentation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import cz.zcu.students.lostandfound.common.extensions.findActivity

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CurrentLocationLocator(
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (!locationPermissionsState.allPermissionsGranted) {
        LaunchedEffect(Unit) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }

    if (locationPermissionsState.revokedPermissions.size
        != locationPermissionsState.permissions.size) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            val activity = context.findActivity()
            if (activity != null) {
                val usedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(activity)
                mapViewModel.getDeviceLocation(usedLocationProviderClient)
            }
        }
    }
}