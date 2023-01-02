package cz.zcu.students.lostandfound.common.util

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Response<T>()

    data class Error<out T>(
        val error: Exception,
        val data: T? = null
    ) : Response<T>()
}
