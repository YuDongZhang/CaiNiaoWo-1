package com.cainiaowo.mine

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.mine.network.IMineService
import com.cainiaowo.mine.repo.IMineResource
import com.cainiaowo.mine.repo.MineRepo
import com.cainiaowo.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * mine模块相关的koin的module配置
 */
val moduleMine = module {

    // Retrofit service
    single {
        KtRetrofit.initConfig("https://course.api.cniao5.com/")
            .getService(IMineService::class.java)
    }

    // MineRepo
    single { MineRepo(get()) } bind IMineResource::class

    // ViewModel
    viewModel { MineViewModel(get()) }

}