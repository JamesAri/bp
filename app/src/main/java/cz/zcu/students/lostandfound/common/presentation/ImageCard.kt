package cz.zcu.students.lostandfound.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.ui.theme.spacing
import kotlin.random.Random

@Composable
fun ImageCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://picsum.photos/seed/${Random.nextInt()}/300/200"
            ),
            contentDescription = null,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
        )
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = MaterialTheme.spacing.small,
                mainAxisSize = SizeMode.Wrap,
            ) {
                AssistChip(
                    onClick = {
                        /*TODO*/
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Call,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Contact person")
                    }
                )
                AssistChip(
                    onClick = {
                        /*TODO*/
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = "Share with others")
                    }
                )

            }
        }
    }
}