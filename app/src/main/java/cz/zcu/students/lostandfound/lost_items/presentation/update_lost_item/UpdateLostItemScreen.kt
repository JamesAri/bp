package cz.zcu.students.lostandfound.lost_items.presentation.update_lost_item

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.lost_items.presentation.lost_items.LostItemViewModel

@Composable
fun UpdatePostScreen(
    viewModel: LostItemViewModel = hiltViewModel(),
    lostItemId: String,
    navigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
//        viewModel.getLostItem(lostItemId)
    }

    Log.i("zcu", "Updating ${viewModel.lostItemState.data}")

    /* TODO: Components */

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onTertiary)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onTertiaryContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurfaceVariant)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inverseSurface)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onError)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outlineVariant)
        ) {}
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.scrim)
        ) {}
    }
}