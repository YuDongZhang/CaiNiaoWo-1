package com.cainiaowo.study

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.study.network.IStudyService
import com.cainiaowo.study.repo.IStudyResource
import com.cainiaowo.study.repo.StudyRepo
import com.cainiaowo.study.viewmodel.StudyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * 学习中心模块相关的koin的module配置
 */
val moduleStudy = module {

    // Retrofit service
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(IStudyService::class.java)
    }

    // MineRepo
    single { StudyRepo(get()) } bind IStudyResource::class

    // ViewModel
    viewModel { StudyViewModel(get()) }

}