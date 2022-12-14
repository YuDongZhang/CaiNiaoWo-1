package com.cainiaowo.login

import com.cainiaowo.login.net.LoginReqBody
import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * 登录模块的接口
 */
interface LoginService {

    /**
     * 使用协程
     */
    //因为在 iloginresourece里面用的就是挂起函数
    @GET("accounts/phone/is/register")
    fun checkRegister(@Query("mobi") mobi: String): Call<BaseCaiNiaoRsp>

    @POST("accounts/course/10301/login")
    fun login(@Body reqBody: LoginReqBody): Call<BaseCaiNiaoRsp>

}