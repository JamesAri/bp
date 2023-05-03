package cz.zcu.students.lostandfound.features.settings.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection
import cz.zcu.students.lostandfound.R
import cz.zcu.students.lostandfound.features.settings.domain.model.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import cz.zcu.students.lostandfound.ui.theme.spacing

/** Screen with app settings. */
@Composable
fun SettingsScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        SettingsList()
    }
}

/**
 * List of all app settings.
 *
 * @param appSettingsViewModel app settings viewmodel.
 */
@Composable
fun SettingsList(
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    var currentThemeText by remember { mutableStateOf("") }
    var currentLanguageText by remember { mutableStateOf("") }

    val themeDialogState = rememberSheetState()
    val languageDialogState = rememberSheetState()

    val theme = appSettingsViewModel.themeState
    val language = appSettingsViewModel.languageState

    val context = LocalContext.current

    LaunchedEffect(theme, language) {
        currentThemeText = when (theme) {
            ThemeOptions.SYSTEM -> context.getString(R.string.screen_settings_theme_system)
            ThemeOptions.DARK -> context.getString(R.string.screen_settings_theme_dark)
            ThemeOptions.LIGHT -> context.getString(R.string.screen_settings_theme_light)
        }
    }

    LaunchedEffect(language) {
        currentLanguageText = when (language) {
            LanguageOptions.ENGLISH -> context.getString(R.string.screen_settings_language_english)
            LanguageOptions.CZECH -> context.getString(R.string.screen_settings_language_czech)
        }
    }

    SettingsItem(
        title = stringResource(R.string.screen_settings_item_theme),
        currentValue = currentThemeText,
        onClick = {
            themeDialogState.show()
        }
    )
    SettingsItem(
        title = stringResource(R.string.screen_settings_item_language),
        currentValue = currentLanguageText,
        onClick = {
            languageDialogState.show()
        }
    )

    ThemeDialog(
        dialogState = themeDialogState,
    )
    LanguageDialog(
        dialogState = languageDialogState,
    )
}

/**
 * Dialog to show when user wants to change the app language.
 *
 * @param dialogState dialog state, i.e. closed/open.
 * @param appSettingsViewModel app settings viewmodel.
 */
@Composable
fun LanguageDialog(
    dialogState: SheetState,
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val language = appSettingsViewModel.languageState

    val context = LocalContext.current

    var dialogTitleState by remember { mutableStateOf("") }
    var englishState by remember { mutableStateOf("") }
    var czechState by remember { mutableStateOf("") }

    LaunchedEffect(language) {
        dialogTitleState = context.getString(R.string.screen_settings_language_dialog_title)
        englishState = context.getString(R.string.screen_settings_language_english)
        czechState = context.getString(R.string.screen_settings_language_czech)
    }

    ListDialog(
        state = dialogState,
        header = Header.Default(title = dialogTitleState),
        selection = ListSelection.Single(
            showRadioButtons = true,
            options = listOf(
                ListOption(
                    titleText = englishState,
                    selected = language == LanguageOptions.ENGLISH,
                ),
                ListOption(
                    titleText = czechState,
                    selected = language == LanguageOptions.CZECH,
                ),
            ),
            positiveButton = null,
        ) { index, _ ->
            when (index) {
                0 -> appSettingsViewModel.setLanguage(LanguageOptions.ENGLISH)
                1 -> appSettingsViewModel.setLanguage(LanguageOptions.CZECH)
            }
        }
    )
}


/**
 * Dialog to show when user wants to change the app theme.
 *
 * @param dialogState dialog state, i.e. closed/open.
 * @param appSettingsViewModel app settings viewmodel.
 */
@Composable
fun ThemeDialog(
    dialogState: SheetState,
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val theme = appSettingsViewModel.themeState
    val language = appSettingsViewModel.languageState

    val context = LocalContext.current

    var dialogTitleState by remember { mutableStateOf("") }
    var systemState by remember { mutableStateOf("") }
    var darkState by remember { mutableStateOf("") }
    var lightState by remember { mutableStateOf("") }

    LaunchedEffect(language) {
        dialogTitleState = context.getString(R.string.screen_settings_theme_dialog_title)
        systemState = context.getString(R.string.screen_settings_theme_system)
        darkState = context.getString(R.string.screen_settings_theme_dark)
        lightState = context.getString(R.string.screen_settings_theme_light)
    }

    ListDialog(
        state = dialogState,
        header = Header.Default(title = dialogTitleState),
        selection = ListSelection.Single(
            showRadioButtons = true,
            options = listOf(
                ListOption(
                    titleText = systemState,
                    selected = theme == ThemeOptions.SYSTEM,
                ),
                ListOption(
                    titleText = darkState,
                    selected = theme == ThemeOptions.DARK,
                ),
                ListOption(
                    titleText = lightState,
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

/**
 * Represents one item in the list of available settings.
 *
 * @param title title with app setting.
 * @param currentValue current value fo the app setting.
 * @param onClick will be called when user clicks on the element.
 */
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
    Divider()
}