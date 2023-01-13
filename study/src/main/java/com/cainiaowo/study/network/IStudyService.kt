package com.cainiaowo.study.network

import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Study模块网络接口
 */
interface IStudyService {

    @GET("member/study/info")
    fun getStudyInfo(): Call<BaseCaiNiaoRsp>

    @GET("member/courses/studied")
    fun getStudyList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<BaseCaiNiaoRsp>

    @GET("member/courses/bought")
    fun getBoughtCourse(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<BaseCaiNiaoRsp>

}