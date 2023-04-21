package cz.zcu.students.lostandfound.features.lost_items.presentation.mark_lost_item

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.constants.Maps.Companion.FAV_LOCATION
import cz.zcu.students.lostandfound.common.features.map.domain.model.LocationCoordinates
import cz.zcu.students.lostandfound.common.features.map.presentation.MapViewModel
import cz.zcu.students.lostandfound.common.features.map.presentation.components.CurrentLocationLocator
import cz.zcu.students.lostandfound.common.features.map.presentation.util.centerOnLocation
import cz.zcu.students.lostandfound.common.features.map.presentation.util.toLocationCoordinates
import cz.zcu.students.lostandfound.ui.theme.spacing


@Composable
fun Map(
    mapViewModel: MapViewModel = hiltViewModel(),
    navigateBack: (LocationCoordinates?) -> Unit,
) {
    val context = LocalContext.current
    val locale = ConfigurationCompat.getLocales(LocalConfiguration.current).get(0)
    var text by rememberSaveable { mutableStateOf("") }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                // Only enable if user has accepted location permissions.
                isMyLocationEnabled = mapViewModel.lastKnownLocation != null,
            )
        )
    }
    var pos by rememberSaveable { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(FAV_LOCATION, 15f)
    }

    LaunchedEffect(mapViewModel.searchLocation) {
        if (mapViewModel.searchLocation != null) {
            val location = mapViewModel.searchLocation!!
            cameraPositionState.centerOnLocation(location)
        }
    }

    LaunchedEffect(mapViewModel.lastKnownLocation) {
        if (mapViewModel.lastKnownLocation != null) {
            val location = mapViewModel.lastKnownLocation!!
            cameraPositionState.centerOnLocation(location)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            cameraPositionState = cameraPositionState,
            onMapClick = {
                pos = it
            }
        ) {
            if (pos != null) {
                Marker(
                    state = MarkerState(position = pos!!),
                    title = stringResource(R.string.screen_lost_item_mark_location_prompt),
                    snippet = stringResource(R.string.screen_lost_item_mark_description),
                    draggable = true,
                )
            }
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(MaterialTheme.spacing.small),
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
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                modifier = Modifier.shadow(15.dp),
                enabled = pos != null,
                onClick = {
                    navigateBack(pos?.toLocationCoordinates())
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(disabledContainerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = stringResource(R.string.screen_lost_item_confirm_action))
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
            Button(
                modifier = Modifier.shadow(15.dp),
                onClick = {
                    navigateBack(null)
                },
                shape = RectangleShape,
            ) {
                Text(text = stringResource(R.string.screen_lost_item_cancel_action))
            }
        }
    }
}


@Composable
fun MarkLostItemScreen(
    navigateBack: (LocationCoordinates?) -> Unit,
) {
    CurrentLocationLocator()
    Map(navigateBack = navigateBack)
}

