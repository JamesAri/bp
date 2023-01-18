package cz.zcu.students.lostandfound.common.auth.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cz.zcu.students.lostandfound.common.components.ProgressBar
import cz.zcu.students.lostandfound.common.util.Response

@Composable
fun <T> ResponseHandler(
    response: Response<T?>,
    onSuccessNullContent: @Composable () -> Unit = {},
    onSuccessContent: @Composable (T) -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (response) {
                    is Response.Loading -> ProgressBar()
                    is Response.Success -> {
                        if (response.data == null) {
                            onSuccessNullContent()
                        } else {
                            onSuccessContent(response.data)
                        }
                    }
                    is Response.Error -> LaunchedEffect(Unit) {
                        val errorMessage = "Error: ${response.error.message}"
                        Log.d("ResponseHandler", errorMessage)
                        snackbarHostState.showSnackbar(
                            message = errorMessage,
                            duration = SnackbarDuration.Long,
                        )
                    }
                }
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResponseSnackBarHandler(
    response: Response<Boolean>,
    onNullMessage: String? = null,
    onFalseMessage: String? = null,
    onTrueMessage: String? = null,
    snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    snackbarHostState: SnackbarHostState,
) {
    when (response) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> LaunchedEffect(Unit) {
            if (response.data == null) {
                if (onNullMessage != null)
                    snackbarHostState.showSnackbar(
                        message = onNullMessage,
                        duration = snackbarDuration,
                    )
            } else {
                if (response.data) {
                    if (onTrueMessage != null)
                        snackbarHostState.showSnackbar(
                            message = onTrueMessage,
                            duration = snackbarDuration,
                        )

                } else {
                    if (onFalseMessage != null)
                        snackbarHostState.showSnackbar(
                            message = onFalseMessage,
                            duration = snackbarDuration,
                        )
                }
            }
        }
        is Response.Error -> LaunchedEffect(Unit) {
            val errorMessage = "Error: ${response.error.message}"
            Log.d("ResponseHandler", errorMessage)
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Long,
            )
        }
    }
}

