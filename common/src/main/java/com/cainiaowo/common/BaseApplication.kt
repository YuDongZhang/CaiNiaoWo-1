package com.cainiaowo.common

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * 抽象的公用BaseApplication
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)

            // 把application传给Koin依赖框架,可以在其它地方使用
            androidContext(this@BaseApplication)

            // modules()    // 依赖注入模块
        }
        initConfig()
        initData()

        LogUtils.d("BaseApplication onCreate")
    }

    /**
     * 初始化配置
     */
    protected open fun initConfig() {}

    /**
     * 初始化数据
     */
    protected open fun initData() {}
}