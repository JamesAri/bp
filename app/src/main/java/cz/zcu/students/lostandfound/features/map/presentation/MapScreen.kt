package cz.zcu.students.lostandfound.features.map.presentation

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.ConfigurationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import cz.zcu.students.lostandfound.common.constants.General.Companion.FAV_LOCATION
import cz.zcu.students.lostandfound.common.extensions.findActivity
import cz.zcu.students.lostandfound.features.map.presentation.util.centerOnLocation
import cz.zcu.students.lostandfound.features.map.presentation.util.toLostItemLocation
import cz.zcu.students.lostandfound.ui.theme.spacing


@Composable
fun Map(
    mapViewModel: MapViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val locale = ConfigurationCompat.getLocales(LocalConfiguration.current).get(0)
    var text by rememberSaveable { mutableStateOf("") }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                // Only enable if user has accepted location permissions.
                isMyLocationEnabled = mapViewModel.state.lastKnownLocation != null,
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(FAV_LOCATION, 15f)
    }

    LaunchedEffect(mapViewModel.searchLocation) {
        if (mapViewModel.searchLocation != null) {
            val location = mapViewModel.searchLocation!!
            cameraPositionState.centerOnLocation(location)
        }
    }

    LaunchedEffect(mapViewModel.state) {
        if (mapViewModel.state.lastKnownLocation != null) {
            val location = mapViewModel.state.lastKnownLocation!!.toLostItemLocation()
            cameraPositionState.centerOnLocation(location)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            cameraPositionState = cameraPositionState
        ) {
        }

        TextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            shape = RectangleShape,
            placeholder = {Text("Search for a location")},
            trailingIcon = {
                IconButton(
                    onClick = {
                        mapViewModel.getLatitudeFromName(context, locale, text)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search location"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(MaterialTheme.spacing.small)
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
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

    Map()
}


//            Marker(
//                state = MarkerState(position = fav),
//                title = "fav",
//                snippet = "Marker in fav"
//            )

//            MarkerInfoWindow(
//                state = rememberMarkerState(position = fav),
//                snippet = "Some stuff",
//                onClick = {
//                    // This won't work :(
//                    System.out.println("Mitchs_: Cannot be clicked")
//                    true
//                },
//                draggable = true
//            ) {
//                Column {
//                    Text("WOW")
//                }
//            }