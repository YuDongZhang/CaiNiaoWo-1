package com.cainiaowo.login.repo

import com.cainiaowo.login.net.LoginReqBody

/**
 * 登录模块相关的抽象数据接口
 */
interface ILoginResource {

    /**
     * 校验手机号是否注册、合法
     */
    suspend fun checkRegister(mobi:String)

    /**
     * 手机号合法的基础上,调用登录,获取登录结果token
     */
    suspend fun requestLogin(body:LoginReqBody)
}