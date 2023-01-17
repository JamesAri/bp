package cz.zcu.students.lostandfound.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.common.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.ui.theme.PreviewTheme
import cz.zcu.students.lostandfound.ui.theme.spacing

// Button(onClick = {
//            authViewModel.logout()
//            navigateToLoginScreen()
//        }) {
//            Text(text = "Logout")
//        }

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {
    val currentUser = authViewModel.currentUser

    currentUser?.let { user ->
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .aspectRatio(1f)
                    .padding(MaterialTheme.spacing.medium),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = user.photoUri,
                    contentDescription = "profile picture",
                    error = painterResource(id = R.drawable.no_image_placeholder),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(.80f)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.large),
                )
                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onClick = {
                        /*TODO*/
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                    )
                }
            }

            ProfileField(
                icon = Icons.Default.Person,
                title = "Name",
                value = user.name,
            )

            ProfileField(
                icon = Icons.Default.Email,
                title = "Email",
                value = user.email,
            )

            val phoneNumber =
                if (user.phoneNumber.isNullOrBlank()) "not added yet" else user.phoneNumber

            ProfileField(
                icon = Icons.Default.Phone,
                title = "Phone Number",
                value = phoneNumber,
            )
        }
    }
}

@Composable
fun ProfileField(
    icon: ImageVector,
    title: String,
    value: String,
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
            Column(
                Modifier
                    .fillMaxWidth()
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
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PreviewTheme() {
        ProfileScreen() {}
    }
}