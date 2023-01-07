package com.cainiaowo.login


import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.login.repo.ILoginResource
import com.cainiaowo.login.repo.LoginRepo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * login模块相关的koin的module配置
 */
val moduleLogin = module {

    // Retrofit service
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(LoginService::class.java)
    }

    // LoginRepo repo   这里bind的意义是因为在LoginViewModel中需要的是ILoginResource接口而不是LoginRepo类
    single { LoginRepo(get()) } bind ILoginResource::class

    // ViewModel  有上面这一步的bing ILoginResource::class 这里才能用get(),一直报错 ,
    viewModel { LoginViewModel(get()) }
}