package com.cainiaowo.login.repo

import androidx.lifecycle.LiveData
import com.cainiaowo.login.network.LoginReqBody
import com.cainiaowo.login.network.LoginRsp
import com.cainiaowo.login.network.RegisterRsp

/**
 * 登录模块相关的抽象数据接口
 *
 */
interface ILoginResource {

    /**
     * 是否已经注册的检查结果
     */
    val registerRsp: LiveData<RegisterRsp>

    /**
     * 登录成功后的结果
     */
    val loginRsp: LiveData<LoginRsp>

    /**
     * 校验手机号是否注册、合法
     */
    suspend fun checkRegister(mobi: String)

    /**
     * 手机号合法的基础上,调用登录,获取登录结果token
     */
    suspend fun requestLogin(body: LoginReqBody)
}