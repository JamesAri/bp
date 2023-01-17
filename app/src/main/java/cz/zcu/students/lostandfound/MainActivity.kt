package cz.zcu.students.lostandfound

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cz.zcu.students.lostandfound.common.Constants.Companion.SPLASHSCREEN_DURATION
import cz.zcu.students.lostandfound.common.auth.presentation.login.AuthViewModel
import cz.zcu.students.lostandfound.navigation.App
import cz.zcu.students.lostandfound.ui.theme.LostAndFoundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSplashScreen()
        setContent {
            LostAndFoundTheme() {
                App()
            }
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