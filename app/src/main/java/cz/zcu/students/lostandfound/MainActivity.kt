package cz.zcu.students.lostandfound

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
import cz.zcu.students.lostandfound.features.settings.domain.language.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.presentation.settings.SettingsViewModel
import cz.zcu.students.lostandfound.navigation.App
import cz.zcu.students.lostandfound.ui.theme.LostAndFoundTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSplashScreen()
        setContent {
            LostAndFoundTheme {
                ConfigChangeListener()
                App()
            }
        }
    }

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


    private fun handleSplashScreen() {
        var splashScreenStays = true
        val delayTime = SPLASHSCREEN_DURATION

        installSplashScreen().setKeepOnScreenCondition { splashScreenStays }
        Handler(Looper.getMainLooper()).postDelayed({ splashScreenStays = false }, delayTime)
        installSplashScreen()
    }
}
