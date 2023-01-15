package com.cainiaowo.course.network

import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ICourseService {

    /**
     * 课程分类
     */
    @GET("course/category")
    fun getCourseCategory(): Call<BaseCaiNiaoRsp>

    /**
     * 课程列表
     * [courseType] 类型 (-1 全部) (1 普通课程) (2 职业课程/班级课程) (3 实战课程) 默认 -1
     * [code] 课程关于哪个方面 从课程分类接口获取 默认 "all" ,例如android等
     * [difficulty] 难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
     * [isFree] 价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
     * [q] 排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
     * [page] 页码
     * [size] 每页显示数
     */
    @GET("course/v2/list")
    fun getCourseList(
        @Query("course_type") courseType: Int,
        @Query("code") code: String,
        @Query("difficulty") difficulty: Int,
        @Query("is_free") isFree: Int,
        @Query("q") q: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<BaseCaiNiaoRsp>

}