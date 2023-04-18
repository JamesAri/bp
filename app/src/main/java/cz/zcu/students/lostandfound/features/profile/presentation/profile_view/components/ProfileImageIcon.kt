package cz.zcu.students.lostandfound.features.profile.presentation.profile_view.components

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun ProfileImageIcon(
    photoUri: Uri?,
    onEditClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(.5f)
            .aspectRatio(1f)
            .padding(MaterialTheme.spacing.medium),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = photoUri,
            contentDescription = stringResource(R.string.screen_profile_content_descriptor_profile_picture),
            error = painterResource(id = R.drawable.no_image_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(.80f)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.large)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.large
                ),
        )
        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = onEditClick,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.screen_profile_edit_action),
            )
        }
    }

}