package cz.zcu.students.lostandfound.features.lost_items.presentation.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun ContactAndShareAssistChips(
    modifier: Modifier = Modifier,
    mainAxisAlignment: FlowMainAxisAlignment = FlowMainAxisAlignment.Start,
    onContactPerson: () -> Unit,
    onShareWithOthers: () -> Unit,
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
            text = "Share with others",
            icon = Icons.Outlined.Share,
            onClick = onShareWithOthers,
        )
    }
}
