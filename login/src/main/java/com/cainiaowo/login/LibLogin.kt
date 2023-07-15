package com.cainiaowo.login

import com.cainiaowo.common.network.KtRetrofit
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.login.network.ILoginService
import com.cainiaowo.login.repo.ILoginResource
import com.cainiaowo.login.repo.LoginRepo
import com.cainiaowo.login.viewmodel.LoginViewModel
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
            .getService(ILoginService::class.java)
    }

//    single {
//        KtRetrofit.initConfig("https://www.baidu.com").getService(ILoginService::class.java)
//    }

    // LoginRepo repo   这里bind的意义是因为在LoginViewModel中需要的是ILoginResource接口而不是LoginRepo类
    //不用 bind 下面是get() 不到的
    single { LoginRepo(get()) } bind ILoginResource::class

    //需要把   LoginViewModel  进行依赖注入 , 点 LoginViewModel 进去看 ,
    // class LoginViewModel(private val resource: ILoginResource) : BaseViewModel() {
    // 需要一个 参数 private val resource: ILoginResource
    //所以要实例化一个  ILoginResource 实现类 , LoginRepo
    //点击  LoginRepo进去看 需要一个 class LoginRepo(private val service: ILoginService) : ILoginResource {
    //可以看到需要一个 service , 最上面的模块就是需要 retrofit  service
    //看最上面的 , retrofit.getService 就是获取一个 service

    // ViewModel
    viewModel { LoginViewModel(get()) }
}