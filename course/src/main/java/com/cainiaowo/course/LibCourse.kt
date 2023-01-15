package com.cainiaowo.course

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.course.network.ICourseService
import com.cainiaowo.course.repo.CourseRepo
import com.cainiaowo.course.repo.ICourseResource
import com.cainiaowo.course.viewmodel.CourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val moduleCourse = module {

    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(ICourseService::class.java)
    }

    single { CourseRepo(get()) } bind ICourseResource::class

    viewModel { CourseViewModel(get()) }

}