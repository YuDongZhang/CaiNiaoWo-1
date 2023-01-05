package com.cainiaowo.mine.network

import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Mine模块网络接口
 */
interface IMineService {

    /**
     * 用户详情信息的获取
     */
    @GET("/member/userinfo")
    fun getUserInfo(@Header("token") token: String?): Call<BaseCaiNiaoRsp>

}