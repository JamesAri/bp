package cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.features.auth.domain.user.User
import cz.zcu.students.lostandfound.common.util.getFormattedDateString
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.shared.ContactAndShareAssistChips
import cz.zcu.students.lostandfound.features.lost_items.presentation.find_lost_item.dialogs.ContactPersonDialog
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    lostItemData: Pair<LostItem, User>,
    navigateToLostItemDetail: (String) -> Unit,
) {
    val (lostItem, postOwner) = lostItemData

    ImageCardWithPostInfo(
        modifier = modifier,
        lostItem = lostItem,
        postOwner = postOwner,
        navigateToLostItemDetail = navigateToLostItemDetail
    )
}

@Composable
fun ImageCardWithPostInfo(
    modifier: Modifier = Modifier,
    lostItem: LostItem,
    postOwner: User,
    navigateToLostItemDetail: (String) -> Unit,
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
            onShareWithOthers = {}
        )
    }
}

@Composable
fun CardFooter(
    lostItem: LostItem,
    postOwner: User,
    onShareWithOthers: () -> Unit,
) {
    var openDialogState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
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
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        ContactAndShareAssistChips(
            onContactPerson = {
                openDialogState = true
            },
            onShareWithOthers = onShareWithOthers,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    ContactPersonDialog(
        postOwner = postOwner,
        openDialogState = openDialogState,
        onDismissRequest = { openDialogState = false },
    )
}


@Composable
fun CardHeader(
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

@Composable
fun ItemPostDate(postTimestamp: Long?) {
    postTimestamp?.let { timestamp ->
        var date by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            date = getFormattedDateString(timestamp) ?: "date not available"
        }

        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun PostOwnerInfo(owner: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = owner.photoUri,
            contentDescription = "post owner info",
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
