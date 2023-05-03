package cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * General assist chip component with themed design.
 *
 * @param modifier the modifier to be applied to the layout.
 * @param text to show on the assist chip.
 * @param icon to show on the assist chip.
 * @param enabled `true` if assist chip is enabled, `false` otherwise.
 * @param onClick will be called when user clicks on the element.
 */
@Composable
fun ThemedAssistChip(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    AssistChip(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = AssistChipDefaults.assistChipColors(
            leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = MaterialTheme.colorScheme.primary,
        ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        label = {
            Text(text = text)
        }
    )
}


