package com.cainiaowo.service.assistant

import android.app.Application
import com.didichuxing.doraemonkit.DoKit

/**
 * 配置dokit的工具类
 */
object AssistantApp {

    fun initConfig(application: Application) {
        DoKit.Builder(application)
            .build()
    }
}