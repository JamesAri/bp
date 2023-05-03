package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.features.auth.domain.model.User
import cz.zcu.students.lostandfound.common.util.datetime.getFormattedDateString
import cz.zcu.students.lostandfound.features.lost_items.domain.model.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.components.dialogs.ContactPersonDialog
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.components.ContactAndMapMarkerAssistChips
import cz.zcu.students.lostandfound.ui.theme.spacing

/**
 * Image card component.
 *
 * @param modifier the modifier to be applied to the layout.
 * @param lostItemData paired lost items and their corresponding post owners.
 * @param navigateToLostItemDetail navigates to lost item detail.
 * @param navigateToMapMarker navigates to marker on the map based on the location.
 */
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    lostItemData: Pair<LostItem, User>,
    navigateToLostItemDetail: (String) -> Unit,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    val (lostItem, postOwner) = lostItemData

    ImageCardWithPostInfo(
        modifier = modifier,
        lostItem = lostItem,
        postOwner = postOwner,
        navigateToLostItemDetail = navigateToLostItemDetail,
        navigateToMapMarker = navigateToMapMarker,
    )
}

/**
 * Image card component with post contact info.
 *
 * @param modifier the modifier to be applied to the layout.
 * @param lostItem lost item to display.
 * @param postOwner owner of the post.
 * @param navigateToLostItemDetail navigates to lost item detail.
 * @param navigateToMapMarker navigates to marker on the map based on the location.
 */
@Composable
private fun ImageCardWithPostInfo(
    modifier: Modifier = Modifier,
    lostItem: LostItem,
    postOwner: User,
    navigateToLostItemDetail: (String) -> Unit,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .clickable {
                navigateToLostItemDetail(lostItem.id)
            }
    ) {

        CardHeader(
            owner = postOwner,
            lostItem = lostItem
        )

        AsyncImage(
            model = lostItem.imageUri,
            contentDescription = lostItem.title,
            error = painterResource(id = R.drawable.no_image_placeholder),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
        )

        CardFooter(
            lostItem = lostItem,
            postOwner = postOwner,
            navigateToMapMarker = navigateToMapMarker,
        )
    }
}

/**
 * Card footer component.
 *
 * @param lostItem lost item to display.
 * @param postOwner owner of the post.
 * @param navigateToMapMarker navigates to marker on the map based on the location.
 */
@Composable
private fun CardFooter(
    lostItem: LostItem,
    postOwner: User,
    navigateToMapMarker: (Double, Double) -> Unit,
) {
    var openDialogState by remember { mutableStateOf(false) }
    val latitude = lostItem.location?.latitude
    val longitude = lostItem.location?.longitude
    val locationProvided = latitude != null && longitude != null
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.small),
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth(),
        ) {
            Text(
                text = lostItem.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = lostItem.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        ContactAndMapMarkerAssistChips(
            onContactPerson = {
                openDialogState = true
            },
            onShowMapMarker = {
                if (locationProvided) {
                    navigateToMapMarker(latitude!!, longitude!!)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            locationProvided = locationProvided,
        )
    }

    ContactPersonDialog(
        postOwner = postOwner,
        openDialogState = openDialogState,
        onDismissRequest = { openDialogState = false },
    )
}

/**
 * Card header component.
 *
 * @param owner post owner.
 * @param lostItem lost item to display.
 */
@Composable
private fun CardHeader(
    owner: User,
    lostItem: LostItem,
) {
    FlowRow(
        mainAxisSize = SizeMode.Wrap,
        mainAxisSpacing = MaterialTheme.spacing.small,
        mainAxisAlignment = MainAxisAlignment.SpaceBetween,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth(),
    ) {
        PostOwnerInfo(owner = owner)
        ItemPostDate(postTimestamp = lostItem.createdAt)
    }
}

/**
 * Item post date component.
 *
 * @param postTimestamp of the lost item.
 * @param lostItemViewModel lost item viewmodel.
 */
@Composable
private fun ItemPostDate(
    postTimestamp: Long?,
    lostItemViewModel: LostItemViewModel = hiltViewModel(),
) {
    postTimestamp?.let { timestamp ->
        var date by remember { mutableStateOf("") }

        val dateNotAvailableMessage = stringResource(R.string.screen_lost_item_date_not_available)

        val context = LocalContext.current

        LaunchedEffect(Unit) {
            date = getFormattedDateString(timestamp, lostItemViewModel.getLocaleTimeString(context))
                ?: dateNotAvailableMessage
        }

        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

/**
 * Post owner contact info component.
 *
 * @param owner with contact information.
 */
@Composable
private fun PostOwnerInfo(owner: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = owner.photoUri,
            contentDescription = stringResource(
                R.string.screen_lost_item_post_owner_info_content_description
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = owner.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
