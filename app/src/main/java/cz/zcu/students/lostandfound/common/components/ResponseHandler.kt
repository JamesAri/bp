package cz.zcu.students.lostandfound.common.components

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import cz.zcu.students.lostandfound.common.util.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <T> ResponseHandler(
    response: Response<T?>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    loadingComponent: @Composable () -> Unit = { ProgressBar() },
    onSuccessNullContent: @Composable () -> Unit = {},
    onSuccessContent: @Composable (T) -> Unit = {},
) {
    when (response) {
        is Response.Loading -> loadingComponent()
        is Response.Success -> {
            if (response.data == null) {
                onSuccessNullContent()
            } else {
                onSuccessContent(response.data)
            }
        }
        is Response.Error -> LaunchedEffect(Unit) {
            val errorMessage = "Error: ${response.error.message}"
            Log.e("ResponseHandler", errorMessage)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = errorMessage,
                    duration = SnackbarDuration.Long,
                )
            }
        }
    }

}

@Composable
fun ResponseSnackBarHandler(
    response: Response<Boolean>,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    loadingComponent: @Composable () -> Unit = { ProgressBar() },
    onTrueMessage: String? = null,
    onFalseMessage: String? = null,
    onNullMessage: String? = null,
    onTrueAction: () -> Unit = {},
    onFalseAction: () -> Unit = {},
    onNullAction: () -> Unit = {},
    snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    snackbarHostState: SnackbarHostState,
) {
    when (response) {
        is Response.Loading -> loadingComponent()
        is Response.Success -> LaunchedEffect(Unit) {
            coroutineScope.launch {
                if (response.data == null) {
                    onNullAction()
                    if (onNullMessage != null) {
                        snackbarHostState.showSnackbar(
                            message = onNullMessage,
                            duration = snackbarDuration,
                        )
                    }
                } else {
                    if (response.data) {
                        onTrueAction()
                        if (onTrueMessage != null) {
                            snackbarHostState.showSnackbar(
                                message = onTrueMessage,
                                duration = snackbarDuration,
                            )
                        }
                    } else {
                        onFalseAction()
                        if (onFalseMessage != null) {
                            snackbarHostState.showSnackbar(
                                message = onFalseMessage,
                                duration = snackbarDuration,
                            )
                        }
                    }
                }
            }
        }
        is Response.Error -> LaunchedEffect(Unit) {
            val errorMessage = "Error: ${response.error.message}"
            Log.e("ResponseSnackBarHandler", errorMessage)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = errorMessage,
                    duration = SnackbarDuration.Long,
                )
            }
        }
    }
}

