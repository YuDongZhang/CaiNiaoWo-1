package com.cainiaowo.common.network.model

import retrofit2.Response

// region 数据响应封装方式一
sealed class DataResult<out R> {
    /**
     * 成功状态的时候
     */
    data class Success<out T>(val data: T) : DataResult<T>()

    /**
     * 错误、失败状态的时候
     */
    data class Error(val exception: Exception) : DataResult<Nothing>()

    /**
     * 加载数据中
     */
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * 返回结果是Success且data非null   data是接口返回的data 不是加密的
 */
val DataResult<*>.succeed
    get() = this is DataResult.Success && data != null
// endregion

// region 数据响应封装方式二
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "Resource Success")
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}
// endregion

