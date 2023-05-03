package cz.zcu.students.lostandfound.features.profile.presentation.profile_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Component representing one profile item.
 *
 * @param icon icon representing profile field.
 * @param title title of the profile field.
 * @param value value of the profile field.
 * @param endComponent end component to use, e.g. button.
 */
@Composable
fun ProfileField(
    icon: ImageVector,
    title: String,
    value: String,
    endComponent: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(MaterialTheme.spacing.medium)
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(0.8f),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
            ProfileFieldEndRow(
                title = title,
                value = value,
                endComponent = endComponent,
            )
        }
    }
}

/**
 * Helper component for [ProfileField].
 *
 * @param title title of the profile field.
 * @param value value of the profile field.
 * @param endComponent end component to use, e.g. button.
 */
@Composable
private fun ProfileFieldEndRow(
    title: String,
    value: String,
    endComponent: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(MaterialTheme.spacing.small)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        endComponent()
    }

}