package cz.zcu.students.lostandfound.common.util

/**
 * Class representing the state of network request.
 *
 * @param T expected result of the network request.
 */
sealed class Response<out T> {
    /** Network request is in progress. */
    object Loading : Response<Nothing>()

    /**
     * Network request was successful and requested [data] are ready to read.
     *
     * @param T expected result of the network request.
     * @property data received data.
     */
    data class Success<out T>(
        val data: T?
    ) : Response<T>()

    /**
     * Network request failed and we can access [error] property to receive
     * more information. Some [data] might be partially loaded.
     *
     * @param T expected result of the network request.
     * @property error error with more information about the failure.
     * @property data some of the received data or `null`.
     */
    data class Error<out T>(
        val error: Exception,
        val data: T? = null
    ) : Response<T>()
}
