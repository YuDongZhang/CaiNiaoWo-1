package com.cainiaowo.common

import android.app.Application
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
            androidLogger(Level.ERROR)  // log level Error方能保证这句话不会报错,要么就不写这个

            // 把application传给Koin依赖框架,可以在其它地方使用
            androidContext(this@BaseApplication)

            //modules()
        }
    }
}