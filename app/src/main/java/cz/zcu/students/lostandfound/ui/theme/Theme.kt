package cz.zcu.students.lostandfound.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import cz.zcu.students.lostandfound.features.settings.presentation.settings.SettingsViewModel

//================================================================================================//
//                                         APP'S THEME                                            //
//================================================================================================//


/** Dark theme color scheme */
private val DarkColorScheme = darkColorScheme(
    primary = BlueGrey70,
    onPrimary = BlueGrey20,
    primaryContainer = Grey25,
    onPrimaryContainer = Grey85,
    inversePrimary = Grey40,
    secondary = Grey45,
    onSecondary = Grey5,
    secondaryContainer = Grey60,
    onSecondaryContainer = Grey99,
    tertiary = Coat80,
    onTertiary = Coat20,
    tertiaryContainer = Coat30,
    onTertiaryContainer = Coat90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey90,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Grey20,
    onSurfaceVariant = Grey90,
    outline = Grey80
)

/** Light theme color scheme */
private val LightColorScheme = lightColorScheme(
    primary = BlueGrey45,
    onPrimary = Grey99,
    primaryContainer = Grey90,
    onPrimaryContainer = Grey15,
    inversePrimary = Grey75,
    secondary = Grey50,
    onSecondary = White,
    secondaryContainer = Grey75,
    onSecondaryContainer = Grey10,
    tertiary = Coat80,
    onTertiary = Coat20,
    tertiaryContainer = Coat30,
    onTertiaryContainer = Coat90,
    error = Red40,
    onError = White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Grey99,
    onSurface = Grey10,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = Grey80,
    onSurfaceVariant = Grey20,
    outline = Grey35
)

/**
 * App's theme provider.
 *
 * @param appSettingsViewModel viewmodel with app settings.
 * @param content App's content (content to be themed).
 */
@Composable
fun LostAndFoundTheme(
    appSettingsViewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val systemIsDarkTheme = isSystemInDarkTheme()

    val darkTheme by remember {
        derivedStateOf {
            when (appSettingsViewModel.themeState) {
                ThemeOptions.SYSTEM -> systemIsDarkTheme
                ThemeOptions.DARK -> true
                ThemeOptions.LIGHT -> false
            }
        }
    }

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme

    LaunchedEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = colorScheme.background,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = colorScheme.primaryContainer,
            darkIcons = useDarkIcons
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// (DEV env) Used for UI development
@Suppress("unused")
@Composable
fun PreviewTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme

    LaunchedEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = colorScheme.background,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = colorScheme.primaryContainer,
            darkIcons = useDarkIcons
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}