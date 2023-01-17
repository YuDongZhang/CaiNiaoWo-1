package com.cainiaowo.home.network

import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IHomeService {

    /**
     * 首页顶部Banner
     */
    @GET("ad/new/banner/list")
    fun getBannerList(@Query("type") type: Int = 5): Call<BaseCaiNiaoRsp>

    /**
     * 获取首页模块的title与id
     */
    @GET("allocation/module/list")
    fun getPageModuleList(): Call<BaseCaiNiaoRsp>

    /**
     * 根据首页各个模块的id去获取具体的模块项目
     */
    @GET("allocation/component/list")
    fun getPageModuleItems(@Query("module_id") moduleId: Int): Call<BaseCaiNiaoRsp>

}