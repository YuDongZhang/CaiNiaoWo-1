package com.cainiaowo.login.repo

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.livedata.SingleLiveData
import com.cainiaowo.common.network.support.serverData
import com.cainiaowo.login.LoginService
import com.cainiaowo.login.net.LoginReqBody
import com.cainiaowo.login.net.LoginRsp
import com.cainiaowo.login.net.RegisterRsp
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.service.network.onFailure
import com.cainiaowo.service.network.onSuccess

/**
 * 登录模块相关的数据管理类
 */
class LoginRepo(private val service: LoginService) : ILoginResource {
    //网络改写数据 , ui观察 //private 私有的可以通过内部set 来改变
    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginRsp = SingleLiveData<LoginRsp>()
                            //这种是不能改变的
    override val registerRsp: LiveData<RegisterRsp> = _registerRsp
    override val loginRsp: LiveData<LoginRsp> = _loginRsp

    /**
     * 数据提供者,不具备感知状态,用suspend修饰,
     * 在调用该方法处处理
     */
    override suspend fun checkRegister(mobi: String) {
        //因为要做判断所以要做封装  com/cainiaowo/service/network/NetRspKtx.kt
//        val a = service.checkRegister(mobi).serverData();
//        when(a){
//            is DataResult.Success ->{
//                a.data.data
//            }
//            is DataResult.Error ->{
//
//            }
//        }
        service.checkRegister(mobi)
            .serverData()
            .onSuccess {
                // 接口响应成功时
                onBizError { code, message ->
                    LogUtils.w("是否注册 BizError $code,$message")
                }
                onBizOK<RegisterRsp> { code, data, message ->
                    _registerRsp.value = data
                    LogUtils.i("是否注册 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                // 接口响应失败
                LogUtils.e("是否注册接口异常 ${it.message}")
            }
    }

    override suspend fun requestLogin(body: LoginReqBody) {
        service.login(body)
            .serverData()
            .onSuccess {
                // 接口响应成功时
                onBizError { code, message ->
                    LogUtils.w("登录接口 BizError $code,$message")
                }
                onBizOK<LoginRsp> { code, data, message ->
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