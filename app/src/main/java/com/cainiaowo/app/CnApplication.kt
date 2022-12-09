package com.cainiaowo.app

import com.cainiaowo.common.BaseApplication
import com.cainiaowo.common.ktx.application
import com.cainiaowo.service.assistant.AssistantApp

class CnApplication : BaseApplication() {
    override fun initConfig() {
        super.initConfig()
        AssistantApp.initConfig(application)
    }
}