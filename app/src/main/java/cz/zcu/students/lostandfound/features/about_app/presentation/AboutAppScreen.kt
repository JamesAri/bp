package cz.zcu.students.lostandfound.features.about_app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.zcu.students.lostandfound.R
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
            TitleText(stringResource(R.string.screen_about_uni_developers))
            SubTitleText(text = stringResource(R.string.screen_about_main_dev))
            BodyText(text = stringResource(R.string.screen_about_main_dev_role))

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            TitleText(stringResource(R.string.screen_about_cooperation))
            SubTitleText(text = stringResource(R.string.screen_about_bachelor_supervisor))
            BodyText(text = stringResource(R.string.screen_about_bachelor_supervisor_role))

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            TitleText(stringResource(R.string.screen_about_thanks))
            SubTitleText(text = stringResource(R.string.screen_about_thanks_students))
            BodyText(
                text = stringResource(R.string.screen_about_thanks_students_text),
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.extraLarge)
        ) {
            Text(
                text = stringResource(R.string.screen_about_version, APP_VERSION),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}