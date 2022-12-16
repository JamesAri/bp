//@file:OptIn(ExperimentalMaterial3Api::class) // added opt-in for the whole module to build.gradle
package cz.zcu.students.jetpackcomposetestproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.zcu.students.jetpackcomposetestproject.ui.theme.JetpackComposeTestProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTestProjectTheme {
//                CardDemo()
//                ScaffoldDemo()
//                LazyColumnDemo()
                FlowDemo()
            }
        }
    }
}

@Composable
fun FlowDemo() {
    val viewModel = viewModel<MainViewModel>()
    val time = viewModel.countDownFlow.collectAsState(initial = 10)

    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { println("hi") }) {
            Text(
                text = time.value.toString(),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun LazyColumnDemo() {
    LazyColumn {
        itemsIndexed(
            listOf("This", "is", "awesome", "!")
        ) { index, string ->
            Text(
                text = string,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 24.dp)
            )
        }
    }
}

@Composable
fun SnackBar(
    modifier: Modifier,
    textFieldState: String,
    updateText: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    scopeCoroutine: CoroutineScope
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        TextField(
            value = textFieldState,
            onValueChange = {
                updateText(it)
            },
            singleLine = true,
            label = {
                Text("Enter some text")
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scopeCoroutine.launch {
                snackBarHostState.showSnackbar("Hello $textFieldState")
            }
        }) {
            Text("Greet me")
        }
    }
}

@Composable
fun ScaffoldDemo() {
    var textFieldState by remember {
        mutableStateOf("")
    }
    val scopeCoroutine = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content = { innerPadding ->
            SnackBar(
                Modifier.padding(innerPadding), textFieldState = textFieldState, updateText = {
                    textFieldState = it
                }, snackBarHostState, scopeCoroutine
            )
        })
}


@Composable
fun CardDemo() {
    val painter = painterResource(id = R.drawable.my_img)
    val description = "My favorite profile picture."
    val title = "My favorite profile picture on GitHub!"
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ) {
        ImageCard(painter = painter, contentDescription = description, title = title)
    }
}

@Composable
fun ImageCard(
    painter: Painter, contentDescription: String, title: String, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp,
        ),
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.89f),
                            ),
                            startY = 300f,
                        )
                    )
            ) {}
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart,
            ) {
                Text(
                    title, style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeTestProjectTheme {
        Greeting("Kubik")
    }
}

