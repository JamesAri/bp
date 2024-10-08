package cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Assist chips for show on map action and show contact action.
 *
 * @param modifier the modifier to be applied to the layout.
 * @param mainAxisAlignment main axis alignment.
 * @param onContactPerson function to run on show contact action click.
 * @param onShowMapMarker function to run on show on map action click.
 * @param locationProvided `true` if location of the lost item is provided, `false` otherwise.
 */
@Composable
fun ContactAndMapMarkerAssistChips(
    modifier: Modifier = Modifier,
    mainAxisAlignment: FlowMainAxisAlignment = FlowMainAxisAlignment.SpaceAround,
    onContactPerson: () -> Unit,
    onShowMapMarker: () -> Unit,
    locationProvided: Boolean,
) {
    FlowRow(
        modifier = modifier,
        mainAxisAlignment = mainAxisAlignment,
        mainAxisSpacing = MaterialTheme.spacing.small,
    ) {
        ThemedAssistChip(
            text = stringResource(R.string.screen_lost_item_contact_person_action),
            icon = Icons.Outlined.Phone,
            onClick = onContactPerson,
        )
        ThemedAssistChip(
            enabled = locationProvided,
            text = stringResource(R.string.screen_lost_item_show_on_map_action),
            icon = ImageVector.vectorResource(id = R.drawable.ic_map_with_marker),
            onClick = onShowMapMarker,
        )
    }
}
