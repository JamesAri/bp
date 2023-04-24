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

/**
 * General function used by UI to properly handle network requests. Based
 * on the passed [coroutineScope], the potential error snackbar will live
 * in that scope.
 *
 * This function is used when you want to show some Composable based on the
 * [response] result. If you want show snackbar based on the response, see
 * [ResponseSnackBarHandler].
 *
 * @param response response to observe.
 * @param snackbarHostState state for snackbar host.
 * @param coroutineScope coroutine scope in which error snackbar messages
 *     are preserved.
 * @param loadingComponent component to show when response is
 *     [Response.Loading].
 * @param onSuccessNullContent on [Response.Success] `null` resource.
 * @param onSuccessContent on [Response.Success] `non-null` resource.
 * @param T resource that the response carries.
 */
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

/**
 * General function used by UI to properly handle network requests. The
 * passed [coroutineScope] determines in which context will snackbar
 * messages live.
 *
 * This function is used when you want to call callback function or/and
 * some snackbar message to the user based on the [response] result.
 *
 * @param response response to observe.
 * @param coroutineScope coroutine scope in which snackbar messages are
 *     preserved.
 * @param loadingComponent component to show when response is [Response.Loading].
 * @param onTrueMessage message to show on [Response.Success] being `true`.
 * @param onFalseMessage message to show on [Response.Success] being
 *     `false`.
 * @param onNullMessage message to show on [Response.Success] being
 *     `null`.
 * @param onTrueAction callback on [Response.Success] being `true`.
 * @param onFalseAction callback on [Response.Success] being `false`.
 * @param onNullAction callback on [Response.Success] being `null`.
 * @param snackbarDuration duration of snackbar message.
 * @param snackbarHostState state for snackbar host.
 */
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

