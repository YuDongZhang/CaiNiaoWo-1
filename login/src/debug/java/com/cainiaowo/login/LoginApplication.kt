package com.cainiaowo.login

import com.cainiaowo.common.BaseApplication
import com.cainiaowo.service.moduleService
import org.koin.core.context.loadKoinModules

class LoginApplication : BaseApplication() {

    override fun initConfig() {
        super.initConfig()
        // 配置Koin
        loadKoinModules(moduleService)
        loadKoinModules(moduleLogin)
    }

}