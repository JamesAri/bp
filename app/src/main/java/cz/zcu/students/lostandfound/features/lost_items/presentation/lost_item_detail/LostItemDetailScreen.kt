package cz.zcu.students.lostandfound.features.lost_items.presentation.lost_item_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.components.ResponseHandler
import cz.zcu.students.lostandfound.features.lost_items.domain.lost_item.LostItem
import cz.zcu.students.lostandfound.features.lost_items.presentation.LostItemViewModel
import cz.zcu.students.lostandfound.features.lost_items.presentation.components.ContactAndShareAssistChips
import cz.zcu.students.lostandfound.navigation.LocalSnackbarHostState
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun LostItemDetailScreen(
    lostItemId: String?,
) {
    if (lostItemId != null) {
        LoadLostItem(lostItemId = lostItemId)
    } else {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "couldn't load lost item detail",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun LoadLostItem(
    viewModel: LostItemViewModel = hiltViewModel(),
    lostItemId: String,
) {
    LaunchedEffect(Unit) {
        viewModel.getLostItem(lostItemId)
    }

    ResponseHandler(
        response = viewModel.lostItemState,
        snackbarHostState = LocalSnackbarHostState.current,
        onSuccessContent = { lostItem ->
            LostItemDetail(lostItem = lostItem)
        }
    )
}

@Composable
fun LostItemDetail(
    lostItem: LostItem,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            AsyncImage(
                model = lostItem.imageUri,
                contentDescription = lostItem.title,
                error = painterResource(id = R.drawable.no_image_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = lostItem.title,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = lostItem.description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            contentAlignment = Alignment.BottomCenter
        ) {
            ContactAndShareAssistChips(
                modifier = Modifier.fillMaxWidth(),
                mainAxisAlignment = FlowMainAxisAlignment.SpaceAround,
                onContactPerson = { },
                onShareWithOthers = {},
            )
        }
    }
}

