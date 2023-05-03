package cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.zcu.students.lostandfound.R

/**
 * Image placeholder when we want to indicate the place to fill with image after user
 * gallery action.
 *
 * @param openGallery function to call to open gallery intent.
 * @param uriState uri state of the retrieved image.
 */
@Composable
fun ImagePlaceholder(
    openGallery: () -> Unit,
    uriState: Uri?,
) {
    Box(
        modifier = Modifier
            .clickable(onClick = openGallery)
            .wrapContentSize()
    ) {
        if (uriState != null) {
            AsyncImage(
                model = uriState,
                contentDescription = stringResource(R.string.screen_lost_item_card_image_content_description),
                error = painterResource(id = R.drawable.no_image_placeholder),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.screen_lost_item_choose_image_action),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

}
