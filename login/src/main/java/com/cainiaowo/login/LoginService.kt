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
    @GET("accounts/phone/is/register")
    suspend fun checkRegister(@Query("mobi") mobi: String): Call<BaseCaiNiaoRsp>

    @POST("accounts/course/10301/login")
    suspend fun login(@Body reqBody: LoginReqBody): Call<BaseCaiNiaoRsp>

}