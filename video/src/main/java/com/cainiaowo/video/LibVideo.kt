package com.cainiaowo.video

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.video.network.IVideoService
import com.cainiaowo.video.repo.IVideoResource
import com.cainiaowo.video.repo.VideoRepo
import com.cainiaowo.video.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val moduleVideo = module {

    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(IVideoService::class.java)
    }

    single { VideoRepo(get()) } bind IVideoResource::class

    viewModel { VideoViewModel(get()) }

}