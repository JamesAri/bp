package cz.zcu.students.lostandfound.features.settings.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import cz.zcu.students.lostandfound.ui.theme.spacing

@Composable
fun SettingsScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        ThemeSettingsItem()
        Divider()
    }
}

@Composable
fun ThemeSettingsItem(
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    var currentValue by remember { mutableStateOf("") }
    val themeDialogState = rememberSheetState()
    val theme = appSettingsViewModel.themeState

    LaunchedEffect(theme) {
        currentValue = when (theme) {
            ThemeOptions.SYSTEM -> "System"
            ThemeOptions.DARK -> "Dark"
            ThemeOptions.LIGHT -> "Light"
        }
    }

    SettingsItem(
        title = "Theme",
        currentValue = currentValue,
        onClick = {
            themeDialogState.show()
        }
    )
    ThemeDialog(
        dialogState = themeDialogState,
    )
}

@Composable
fun ThemeDialog(
    dialogState: SheetState,
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val theme = appSettingsViewModel.themeState

    ListDialog(
        state = dialogState,
        header = Header.Default(title = "Theme"),
        selection = ListSelection.Single(
            showRadioButtons = true,
            options = listOf(
                ListOption(
                    titleText = "System",
                    selected = theme == ThemeOptions.SYSTEM,
                ),
                ListOption(
                    titleText = "Dark",
                    selected = theme == ThemeOptions.DARK,
                ),
                ListOption(
                    titleText = "Light",
                    selected = theme == ThemeOptions.LIGHT,
                ),
            ),
            positiveButton = null,
        ) { index, _ ->
            when (index) {
                0 -> appSettingsViewModel.setTheme(ThemeOptions.SYSTEM)
                1 -> appSettingsViewModel.setTheme(ThemeOptions.DARK)
                2 -> appSettingsViewModel.setTheme(ThemeOptions.LIGHT)
            }
        }
    )
}

@Composable
fun SettingsItem(
    title: String,
    currentValue: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = currentValue,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}