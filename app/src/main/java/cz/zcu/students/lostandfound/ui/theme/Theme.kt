package cz.zcu.students.lostandfound.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF35363A),
    onPrimary = Color(0xFFEAF1FB),
    primaryContainer = Color(0xFF1C1B1F),
    onPrimaryContainer = Color(0xFF35363A),
    inversePrimary = Color(0xFFCAC9C5),

    secondary = Color(0xFF3E4558),
    onSecondary = Color(0xFFDCE1F7),
    secondaryContainer = Color(0xFF3F4659),
    onSecondaryContainer = Color(0xFFDCE2F8),

    tertiary = Color(0xFFEFBAB4),
    onTertiary = Color(0xFF561614),
)

// TODO
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFEAF1FB),

    tertiary = Color(0xFFEFBAB4),
    onTertiary = Color(0xFF561614),
)

@Composable
fun LostAndFoundTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    LaunchedEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = colorScheme.background,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = colorScheme.primary,
            darkIcons = useDarkIcons
        )
    }

//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
//            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}