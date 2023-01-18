package com.cainiaowo.video.network

import com.cainiaowo.service.network.BaseCaiNiaoRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IVideoService {

    /**
     * 根据课程ID获取课程学习权限
     * 在用户没有登录的情况下也可以调用,但是会返回false
     */
    @GET("course/authority")
    fun getCoursePermission(@Query("course_id") courseId: Int): Call<BaseCaiNiaoRsp>

    /**
     * 根据课程ID获取课程章节列表(包含章节里面的课时)
     */
    @GET("course/lessons")
    fun getCourseLessons(@Query("course_id") courseId: Int): Call<BaseCaiNiaoRsp>

    /**
     * 课时播放地址
     */
    @GET("lesson/urls")
    fun getLessonUrls(@Query("key") key: String): Call<BaseCaiNiaoRsp>
}