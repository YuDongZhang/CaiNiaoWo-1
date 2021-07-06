package com.cainiaowo.app

import com.alibaba.android.arouter.launcher.ARouter
import com.cainiaowo.common.base.BaseApplication
import com.cainiaowo.common.ktx.application
import com.cainiaowo.login.moduleLogin
import com.cainiaowo.mine.moduleMine
import com.cainiaowo.service.assistant.AssistantApp
import com.cainiaowo.service.moduleService
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

/**
 * Application类
 */
class CaiNiaoApplication : BaseApplication() {

    private val modules = mutableListOf<Module>(moduleService, moduleLogin, moduleMine)

    override fun initConfig() {
        // 由于BaseApplication的initConfig()中没有内容,所以可以省略super.initConfig()
        // 如果需要override函数时强制调用super函数,则需要在父类的对应函数上添加@CallSuper注解
        super.initConfig()

        // 配置koin
        loadKoinModules(modules)

        // dokit初始化配置
        AssistantApp.initConfig(application)

        // ARouter初始化
        ARouter.init(application)
    }

}