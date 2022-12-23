package cz.zcu.students.lostandfound.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cz.zcu.students.lostandfound.ui.theme.LostAndFoundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LostAndFoundTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LostAndFoundTheme {}
}