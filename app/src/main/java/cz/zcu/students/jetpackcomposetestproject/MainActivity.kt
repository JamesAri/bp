//@file:OptIn(ExperimentalMaterial3Api::class) // added opt-in for the whole module, see build.gradle
package cz.zcu.students.jetpackcomposetestproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
//                FlowDemo()
//                AnimationDemo()
//                Navigation()
                BottomNavigationDemo()
            }
        }
    }
}

@Composable
fun BottomNavigationDemo() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Notifications,
                    BottomNavItem.Settings,
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) {
        MyBottomNavigation(navController = navController)
        println(it)
    }
}


@Composable
fun AnimationDemo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressBar(.5f, 320)
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    maxNumber: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp,
    animationDur: Int = 1000,
    animationDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage by animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDur,
            delayMillis = animationDelay,
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier.size(radius * 2f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f,
                360 * curPercentage,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage * maxNumber).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
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

