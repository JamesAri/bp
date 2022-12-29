package cz.zcu.students.lostandfound

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cz.zcu.students.lostandfound.core.Constants.Companion.SPLASHSCREEN_DURATION
import cz.zcu.students.lostandfound.navigation.app_bar.TopAppBar
import cz.zcu.students.lostandfound.ui.theme.LostAndFoundTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSplashScreen()
        setContent {
            LostAndFoundTheme {
//                NavGraph()
                TopAppBar()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //todo check if user logged in
    }

    private fun handleSplashScreen() {
        var splashScreenStays = true
        val delayTime = SPLASHSCREEN_DURATION

        installSplashScreen().setKeepOnScreenCondition { splashScreenStays }
        Handler(Looper.getMainLooper()).postDelayed({ splashScreenStays = false }, delayTime)
        installSplashScreen()

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LostAndFoundTheme {}
}