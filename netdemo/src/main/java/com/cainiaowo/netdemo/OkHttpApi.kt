package com.cainiaowo.netdemo

import com.google.gson.Gson
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * IHttpApi的实现类：使用OkHttp
 */
class OkHttpApi : IHttpApi {

    companion object {
        private const val TAG = "OkHttpApi"   // TAG,用于日志观察
    }

    private var baseUrl = "http://api.qingyunke.com/"

    // OkHttpClient
    private val mClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)       // 完整请求超时时长,从发起到接收返回数据,默认值0,不限定
        .connectTimeout(10, TimeUnit.SECONDS)   // 与服务器建立连接的时长,默认10s
        .readTimeout(10, TimeUnit.SECONDS)      // 读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS)     // 向服务器写入数据的时长,默认10s
        .retryOnConnectionFailure(true) // 重连
        .followRedirects(false)     // 重定向
        .cache(Cache(File("sdcard/cache", "okhttp"), 1024L))    // 网络请求的缓存数据
//        .cookieJar(CookieJar.NO_COOKIES)      // 实现 接口CookieJar的实现类,以此来设置cookie相关的信息,NO_COOKIES只适合无COOKIE使用
        .build()

    override fun get(params: Map<String, Any>, path: String, callback: IHttpCallback) {
        val url = "$baseUrl$path"
        val urlBuilder = url.toHttpUrl().newBuilder()
        params.forEach { entry ->
            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        }
        val request = Request.Builder()
            .get()
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK) // 强制使用网络更新
            .build()
        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }
        })
    }

    override fun post(body: Any, path: String, callback: IHttpCallback) {
        val url = "$baseUrl$path"
        val request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url("https://testapi.cniao5.com/accounts/login")
            .build()
        mClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string())
            }
        })
    }
}