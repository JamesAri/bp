package cz.zcu.students.lostandfound

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import cz.zcu.students.lostandfound.common.constants.Settings.Companion.CZECH_ISO
import cz.zcu.students.lostandfound.common.constants.Settings.Companion.ENGLISH_ISO
import cz.zcu.students.lostandfound.common.constants.Settings.Companion.SPLASHSCREEN_DURATION
import cz.zcu.students.lostandfound.features.settings.domain.model.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.presentation.settings.SettingsViewModel
import cz.zcu.students.lostandfound.navigation.App
import cz.zcu.students.lostandfound.ui.theme.LostAndFoundTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/** Main activity of the app, where we inject our dependencies. */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSplashScreen()
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            LostAndFoundTheme {
                ConfigChangeListener()
                App()
            }
        }
    }

    /**
     * Listener for app locale (language) changes and updates states accordingly.
     *
     * @param appSettingsViewModel viewmodel with app settings.
     */
    @Suppress("DEPRECATION")
    @Composable
    fun ConfigChangeListener(
        appSettingsViewModel: SettingsViewModel = hiltViewModel()
    ) {
        val languageState = appSettingsViewModel.languageState

        val localeISO = when (languageState) {
            LanguageOptions.ENGLISH -> ENGLISH_ISO
            LanguageOptions.CZECH -> CZECH_ISO
        }

        LaunchedEffect(languageState) {
            val config = resources.configuration
            val locale = Locale(localeISO)
            Locale.setDefault(locale)
            config.setLocale(locale)
            createConfigurationContext(config)
            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }


    /** Handles custom splash screen, with duration set to [SPLASHSCREEN_DURATION]. */
    private fun handleSplashScreen() {
        var splashScreenStays = true

        installSplashScreen().setKeepOnScreenCondition { splashScreenStays }
        Handler(Looper.getMainLooper()).postDelayed(
            { splashScreenStays = false },
            SPLASHSCREEN_DURATION
        )
        installSplashScreen()
    }
}
