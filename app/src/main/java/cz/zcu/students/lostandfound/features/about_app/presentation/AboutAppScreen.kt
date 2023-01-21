package cz.zcu.students.lostandfound.features.about_app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.zcu.students.lostandfound.common.constants.General.Companion.APP_VERSION
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
private fun TitleText(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
}

@Composable
private fun SubTitleText(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
private fun BodyText(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary,
    )
}


@Composable
fun AboutAppScreen() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .padding(MaterialTheme.spacing.medium)
        ) {
            TitleText("University app developers")
            SubTitleText(text = "Jakub Šlechta")
            BodyText(text = "Android, as part of bachelor's thesis")

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            TitleText("Cooperation")
            SubTitleText(text = "Ladislav Pešička")
            BodyText(text = "Supervisor of the bachelor's thesis")

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            TitleText("Thanks")
            SubTitleText(text = "Students")
            BodyText(
                text = "Students from all universities which helped us with debugging and troubleshooting.",
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.extraLarge)
        ) {
            Text(
                text = "App version $APP_VERSION",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}