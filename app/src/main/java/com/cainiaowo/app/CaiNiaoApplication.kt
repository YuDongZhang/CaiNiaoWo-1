package com.cainiaowo.app

import com.cainiaowo.common.BaseApplication
import com.cainiaowo.common.ktx.application
import com.cainiaowo.service.assistant.AssistantApp

/**
 * Application类
 */
class CaiNiaoApplication : BaseApplication() {

    override fun initConfig() {
        super.initConfig()

        // dokit初始化配置
        AssistantApp.initConfig(application)
    }

}