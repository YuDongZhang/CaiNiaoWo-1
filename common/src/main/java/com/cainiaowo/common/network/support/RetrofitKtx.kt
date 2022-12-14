package com.cainiaowo.common.network.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


// region Retrofit扩展

/**
 * Retrofit的Call执行异步,并转化为LiveData可观察结果
 */
fun <T : Any> retrofit2.Call<T>.toLiveData(): LiveData<T?> {
    val liveData = MutableLiveData<T>()
    this.enqueue(object : retrofit2.Callback<T> {
        override fun onFailure(call: retrofit2.Call<T>, t: Throwable) {
            liveData.postValue(null)
        }

        override fun onResponse(call: retrofit2.Call<T>, response: retrofit2.Response<T>) {
            val value = if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
            liveData.postValue(value)
        }
    })
    return liveData
}

//
///**
// * 扩展Retrofit的返回数据,调用await,并catch超时等异常
// * @return DataResult 返回格式为ApiResponse封装
// */
//suspend fun <T : Any> retrofit2.Call<T>.serverData(): DataResult<T> {
//    var result: DataResult<T> = DataResult.Loading
//    kotlin.runCatching {
//        this.await()
//    }.onFailure {
//        result = DataResult.Error(RuntimeException(it))
//        it.printStackTrace()
//    }.onSuccess {
//        result = DataResult.Success(it)
//    }
//    return result
//}
//
//
///**
// * 扩展Retrofit的返回数据,调用await,并catch超时等异常
// * @return ApiResponse 返回格式为ApiResponse封装
// */
//suspend fun <T : Any> retrofit2.Call<T>.serverResponse(): ApiResponse<T> {
//    var result: ApiResponse<T>
//    val response = kotlin.runCatching {
//        this.awaitResponse()
//    }.onFailure {
//        result = ApiResponse.create(UNKNOWN_ERROR_CODE, it)
//        it.printStackTrace()
//    }.getOrThrow()
//    result = ApiResponse.create(response)
//    return result
//}

// endregion