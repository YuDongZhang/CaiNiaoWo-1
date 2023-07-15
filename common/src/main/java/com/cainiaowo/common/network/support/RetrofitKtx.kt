package com.cainiaowo.common.network.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cainiaowo.common.network.model.ApiResponse
import com.cainiaowo.common.network.model.DataResult
import com.cainiaowo.common.network.model.UNKNOWN_ERROR_CODE
import retrofit2.Call
import retrofit2.await
import retrofit2.awaitResponse

// region Retrofit扩展

/**
 * Retrofit的Call执行异步,并转化为LiveData可观察结果
 */
fun <T : Any> Call<T>.toLiveData(): LiveData<T?> {
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


/**
 * 扩展Retrofit的返回数据,调用await,并catch超时等异常
 * @return DataResult 返回格式为ApiResponse封装
 * .serveData() 对于call的扩展 , this.await()就是call , service.checkRegister(mobi)本来就可以直接调用 wait
 *  .serveData() 对于call的扩展得到 DataResult
 */
suspend fun <T : Any> Call<T>.serveData(): DataResult<T> {
    var result: DataResult<T> = DataResult.Loading
    kotlin.runCatching {
        this.await() //请求的过程中会报错 , 所以用 this.await()
    }.onFailure {
        result = DataResult.Error(RuntimeException(it))
        it.printStackTrace()
    }.onSuccess {
        result = DataResult.Success(it)
    }
    //上面这块的代码可以理解为 this.await() 有成功和失败 , 失败走  DataResult.Error 成功走  result = DataResult.Success(it)
    return result
}


/**
 * 扩展Retrofit的返回数据,调用await,并catch超时等异常
 * @return ApiResponse 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serveResponse(): ApiResponse<T> {
    var result: ApiResponse<T>
    val response = kotlin.runCatching {
        this.awaitResponse()
    }.onFailure {
        result = ApiResponse.create(UNKNOWN_ERROR_CODE, it)
        it.printStackTrace()
    }.getOrThrow()
    result = ApiResponse.create(response)
    return result
}

// endregion