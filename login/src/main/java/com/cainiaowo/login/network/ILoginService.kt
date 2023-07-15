package com.cainiaowo.login.network

import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * 登录模块的接口 , 就是 retrofit 需要的 service
 */
interface ILoginService {

    //BaseCaiNiaoRsp 结构是差不多的 ,
    //这个地方不用声明为挂起函数 suspend , 在 LoginRepo 用的就是挂起函数.
    @GET("accounts/phone/is/register")
    fun checkRegister(@Query("mobi") mobi: String): Call<BaseCaiNiaoRsp> //这个地方传的是父类 , 就是形式

    @POST("accounts/course/10301/login")
    fun login(@Body reqBody: LoginReqBody): Call<BaseCaiNiaoRsp>

}