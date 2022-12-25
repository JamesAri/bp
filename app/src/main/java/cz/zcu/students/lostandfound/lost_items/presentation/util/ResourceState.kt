package cz.zcu.students.lostandfound.lost_items.presentation.util

import cz.zcu.students.lostandfound.lost_items.domain.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

fun <T> MutableStateFlow<ResourceState<T>>.collectResource(
    coroutineScope: CoroutineScope,
    repoCallback: KSuspendFunction0<Resource<T>>
) {
    coroutineScope.launch {
        update {
            it.setLoading()
        }
        when (val result = repoCallback()) {
            is Resource.Success -> {
                update {
                    it.setData(result.data)
                }
            }
            is Resource.Error -> {
                update {
                    it.setError(result.message)
                }
            }
        }
    }
}

data class ResourceState<out T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

fun <T>ResourceState<T>.setData(data: T?): ResourceState<T> {
    return copy(
        data = data,
        isLoading = false,
        error = null,
    )
}

fun <T>ResourceState<T>.setLoading(): ResourceState<T> {
    return copy(
        isLoading = true,
        error = null,
    )
}

fun <T>ResourceState<T>.setError(errorMessage: String?): ResourceState<T> {
    return copy(
        data = null,
        isLoading = false,
        error = errorMessage,
    )
}