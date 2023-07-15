package com.cainiaowo.login.repo

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.livedata.SingleLiveData
import com.cainiaowo.common.network.model.DataResult
import com.cainiaowo.common.network.model.onFailure
import com.cainiaowo.common.network.model.onSuccess
import com.cainiaowo.common.network.support.serveData
import com.cainiaowo.login.network.ILoginService
import com.cainiaowo.login.network.LoginReqBody
import com.cainiaowo.login.network.LoginRsp
import com.cainiaowo.login.network.RegisterRsp
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import retrofit2.await

/**
 * 登录模块相关的数据管理类
 *
 * repository 仓库的意思
 */
class LoginRepo(private val service: ILoginService) : ILoginResource {

    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginRsp = SingleLiveData<LoginRsp>()
    //上面的写法是内部是可以拿到网络数据源 ,setValue 和 postValue

    //下面这个两个是公开的 , 是在 ILoginResource 里面声明的 , 只能观察,不能setValue 和postValue
    override val registerRsp: LiveData<RegisterRsp> = _registerRsp
    override val loginRsp: LiveData<LoginRsp> = _loginRsp

    /**
     * 数据提供者,不具备感知状态,用suspend修饰,
     * 在调用该方法处处理
     */
    override suspend fun checkRegister(mobi: String) {
        service.checkRegister(mobi)
            .serveData()//点 serveData() 进去看 , 是对call的扩展 , 上面service.checkRegister(mobi) 返回的是 call 点进去看
            .onSuccess {//com.cainiaowo.common.network.model.DataResultKt.onSuccess
                //onsuccess 返回的就是 basecainiaorsp ,可以用this.onBizError
                // 接口响应成功 看下扩展方法 , 学习这个方法 , 成功后还有不同的状态 ,因为还有 data为空情况
                onBizError { code, message ->
                    LogUtils.w("是否注册 BizError $code,$message")
                }
                onBizOK<RegisterRsp> { _, data, _ ->
                    _registerRsp.value = data
                    LogUtils.i("是否注册 BizOK $data")
                    return@onBizOK
                }
//                LogUtils.d(this.code)
            }
            .onFailure {
                // 接口响应失败
                LogUtils.e("是否注册接口异常 ${it.message}")
            }

         //  service.checkRegister(mobi).await() 可以直接 await 这个里面有协程代码的支持 ,做为协程响应,
        //  网络请求的响应


       /*
        //本来应该是下面这样去写
        val a = service.checkRegister(mobi).serveData()
        //这种很多地方都要写 , 所以要进行扩展 ,上面就是得到的 DataResult 对他进行扩展 , 看 common

        //这块要做到  优化  //com.cainiaowo.common.network.support.RetrofitKtxKt.serveData
        when(a){
            is DataResult.Success ->{
                //下面解密的这些 放到哪里了 ?

                //这个data是类中的data {code:"",data:"",message:""} 这个data需要解密成为实体类
                var json = a.data.data
                //这个地方要解密才能走到下面这个一步 ,
                GsonUtils.fromJson<RegisterRsp>(json,registerRsp::class.java)
            }
            is DataResult.Error ->{

            }
            else -> {

            }
        }*/
    }

    override suspend fun requestLogin(body: LoginReqBody) {
        service.login(body)
            .serveData()
            .onSuccess {
                // 接口响应成功时
                onBizError { code, message ->
                    LogUtils.w("登录接口 BizError $code,$message")
                }
                onBizOK<LoginRsp> { _, data, _ ->
                    _loginRsp.value = data
                    LogUtils.i("登录接口 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                // 接口响应失败
                LogUtils.e("登录接口异常 ${it.message}")
            }
    }
}