package cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.ui.theme.spacing

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
        mainAxisSize = SizeMode.Wrap,
    ) {
        ThemedAssistChip(
            text = "Contact person",
            icon = Icons.Outlined.Phone,
            onClick = onContactPerson,
        )
        ThemedAssistChip(
            enabled = locationProvided,
            text = "Show on map",
            icon = ImageVector.vectorResource(id = R.drawable.ic_map_with_marker),
            onClick = onShowMapMarker,
        )
    }
}
