package com.cainiaowo.service

import com.cainiaowo.common.network.KtRetrofit
import org.koin.dsl.module

/**
 * service模块相关的koin的module配置
 */
val moduleService = module {

    single { (host: String) -> KtRetrofit.initConfig(host) }

}