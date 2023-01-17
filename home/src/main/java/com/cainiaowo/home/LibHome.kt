package com.cainiaowo.home

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.home.network.IHomeService
import com.cainiaowo.home.repo.HomeRepo
import com.cainiaowo.home.repo.IHomeResource
import com.cainiaowo.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val moduleHome = module {

    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(IHomeService::class.java)
    }

    single { HomeRepo(get()) } bind IHomeResource::class

    viewModel { HomeViewModel(get()) }

}