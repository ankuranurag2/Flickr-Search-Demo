package dev.ananurag2.flickr.model

/**
 * created by ankur on 2/2/20
 */

/**
 * Class to convert results into generalised results
 */
data class ApiResult<out T>(
    val status: Status,
    val data: T?,
    val msg: String?
) {
    companion object {
        fun <T> success(data: T?): ApiResult<T> {
            return ApiResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): ApiResult<T> {
            return ApiResult(Status.ERROR, data, msg)
        }

        fun <T> done(data: T? = null): ApiResult<T> {
            return ApiResult(Status.DONE, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    DONE
}