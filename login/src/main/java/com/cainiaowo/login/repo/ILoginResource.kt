package com.cainiaowo.login.repo

import androidx.lifecycle.LiveData
import com.cainiaowo.login.net.LoginReqBody
import com.cainiaowo.login.net.LoginRsp
import com.cainiaowo.login.net.RegisterRsp

/**
 * 登录模块相关的抽象数据接口
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
     *
     * //下面这两个的结果以livedata形式返回出来
     * //在对应的repo里面要实现对应的livedata (loginRepo)
     */
    suspend fun checkRegister(mobi:String)

    /**
     * 手机号合法的基础上,调用登录,获取登录结果token
     */
    suspend fun requestLogin(body:LoginReqBody)
}