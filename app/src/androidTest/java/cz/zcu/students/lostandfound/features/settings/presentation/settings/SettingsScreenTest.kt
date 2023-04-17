package cz.zcu.students.lostandfound.features.settings.presentation.settings

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.zcu.students.lostandfound.MainActivity
import cz.zcu.students.lostandfound.navigation.Screen
import cz.zcu.students.lostandfound.ui.theme.LostAndFoundTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            LostAndFoundTheme() {
                NavHost(
                    navController = navController,
                    startDestination = Screen.SettingsScreen.route,
                ) {
                    composable(
                        route = Screen.SettingsScreen.route,
                    ) {
                        SettingsScreen()
                    }
                }
            }
        }
    }

    @Test
    fun whenClickingOnThemeInSettings_dialogOpens() {
        composeRule.onNodeWithText("Theme").performClick()
        composeRule.onAllNodesWithText("Theme").assertCountEquals(2)
    }
}