package cz.zcu.students.jetpackcomposetestproject.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cz.zcu.students.jetpackcomposetestproject.ui.viewmodel.DependencyViewModel

@Composable
fun DependencyScreen(
    dependencyViewModel: DependencyViewModel
) {
    val dependencyString = dependencyViewModel.dependencyString
    val stringOne = dependencyViewModel.stringOne
    val stringTwo = dependencyViewModel.stringTwo

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = dependencyString, fontSize = 32.sp)
        Text(text = stringOne, fontSize = 32.sp)
        Text(text = stringTwo, fontSize = 32.sp)
    }
}