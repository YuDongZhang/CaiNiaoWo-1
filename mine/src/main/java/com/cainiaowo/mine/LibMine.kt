package com.cainiaowo.mine

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.mine.network.IMineService
import com.cainiaowo.mine.repo.IMineResource
import com.cainiaowo.mine.repo.MineRepo
import com.cainiaowo.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * mine模块相关的koin的module配置
 */
val moduleMine = module {

    // Retrofit service
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(IMineService::class.java)
    }

    // MineRepo
    single { MineRepo(get()) } bind IMineResource::class

    // ViewModel
    viewModel { MineViewModel(get()) }

}