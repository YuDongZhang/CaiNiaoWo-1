package com.cainiaowo.common.network.config

import androidx.core.net.toUri
import com.cainiaowo.common.utils.getBaseHost
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 切换Host的拦截器
 */
class HostInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        val oldUrlStr = originRequest.url.toString()
        // host不包含scheme,比如URL:http://android.com/ , HOST:android.com
        val oldHost = originRequest.url.host

        val newHost = getBaseHost().toUri().host ?: oldHost
        val newUrlStr = if (newHost == oldHost) oldUrlStr else oldUrlStr.replace(oldHost, newHost)

        return chain.proceed(originRequest.newBuilder().url(newUrlStr).build())
    }

}