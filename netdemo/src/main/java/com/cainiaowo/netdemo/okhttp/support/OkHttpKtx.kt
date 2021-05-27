package com.cainiaowo.netdemo.okhttp.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import java.io.IOException

// region OkHttp3相关扩展

/**
 * OkHttp的Call执行异步,并转化为LiveData可观察结果
 */
inline fun <reified T> okhttp3.Call.toLiveData(): LiveData<T?> {
    val liveData = MutableLiveData<T>()
    this.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            liveData.postValue(null)
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            if (response.isSuccessful) {
                response.toEntity<T>()
            }
        }
    })
    return liveData
}

/**
 * 将Response的对象转化为需要的对象类型,也就是将body.string转为entity
 * @return 返回需要的类型对象,可能为null,如果json解析失败的话
 */
inline fun <reified T> okhttp3.Response.toEntity(): T? {
    if (!isSuccessful) return null
    // 如果是String,单独处理
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return kotlin.runCatching {
            this.body?.string()
        }.getOrNull() as? T
    }
    return kotlin.runCatching {
        Gson().fromJson(this.body?.string(), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}

// endregion