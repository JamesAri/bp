package cz.zcu.students.lostandfound.features.lost_items.presentation.lost_items_map

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
import androidx.compose.ui.res.stringResource
import androidx.core.os.ConfigurationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.common.constants.Maps.Companion.FAV_LOCATION
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.common.features.map.presentation.components.CurrentLocationLocator
import cz.zcu.students.lostandfound.common.features.map.presentation.MapViewModel
import cz.zcu.students.lostandfound.common.features.map.presentation.util.centerOnLocation
import cz.zcu.students.lostandfound.common.features.map.presentation.util.toLatLng
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components.LostItemsFetchErrorComponent
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing


@Composable
fun Map(
    mapViewModel: MapViewModel = hiltViewModel(),
    lostItems: List<LostItem>,
    focusLocation: LocationCoordinates?,
) {
    val context = LocalContext.current
    val locale = ConfigurationCompat.getLocales(LocalConfiguration.current).get(0)
    var text by rememberSaveable { mutableStateOf("") }
    var properties by remember {
        mutableStateOf(
            MapProperties(
                // Only enable if user has accepted location permissions.
                isMyLocationEnabled = mapViewModel.lastKnownLocation != null,
            )
        )
    }

    val focus = focusLocation?.toLatLng() ?: FAV_LOCATION

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(focus, 15f)
    }

    LaunchedEffect(mapViewModel.searchLocation) {
        if (mapViewModel.searchLocation != null) {
            val location = mapViewModel.searchLocation!!
            cameraPositionState.centerOnLocation(location)
        }
    }

    LaunchedEffect(mapViewModel.lastKnownLocation) {
        if (focusLocation == null && mapViewModel.lastKnownLocation != null) {
            val location = mapViewModel.lastKnownLocation!!
            cameraPositionState.centerOnLocation(location)
            properties = properties.copy(
                isMyLocationEnabled = true
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            cameraPositionState = cameraPositionState,
        ) {
            for (item in lostItems) {
                item.location?.let {
                    Marker(
                        state = MarkerState(position = it.toLatLng()),
                        title = item.title,
                        snippet = item.description,
                    )
                }
            }
        }

        TextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            shape = RectangleShape,
            placeholder = { Text(stringResource(R.string.screen_lost_item_search_for_location_placeholder)) },
            trailingIcon = {
                IconButton(
                    onClick = {
                        mapViewModel.getLatitudeFromName(context, locale, text)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(
                            R.string.screen_lost_item_search_location_content_description
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(MaterialTheme.spacing.small)
        )
    }
}

@Composable
fun LoadMapWithMarkers(
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
    focusLocation: LocationCoordinates?,
) {
    LaunchedEffect(Unit) {
        lostItemViewModel.loadLostItems()
    }

    ResponseHandler(
        response = lostItemViewModel.lostItemListState.collectAsStateWithLifecycle().value,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = { lostItemList ->
            val lostItems = lostItemList.lostItems
            Map(lostItems = lostItems, focusLocation = focusLocation)
        },
        onSuccessNullContent = {
            LostItemsFetchErrorComponent()
        }
    )
}

@Composable
fun LostItemsMap(
    location: LocationCoordinates?,
) {
    CurrentLocationLocator()
    LoadMapWithMarkers(focusLocation = location)
}
