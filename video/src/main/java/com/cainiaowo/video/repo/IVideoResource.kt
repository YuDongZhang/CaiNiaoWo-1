package com.cainiaowo.video.repo

import androidx.lifecycle.LiveData
import com.cainiaowo.common.livedata.SingleLiveData
import com.cainiaowo.video.network.CourseLessonsRsp
import com.cainiaowo.video.network.CoursePermissionRsp
import com.cainiaowo.video.network.LessonUrlsRsp

interface IVideoResource {

    val liveCoursePermission: LiveData<CoursePermissionRsp>
    val liveCourseLessons: SingleLiveData<CourseLessonsRsp>
    val liveLessonUrls: SingleLiveData<LessonUrlsRsp>

    suspend fun getCoursePermission(courseId: Int)

    suspend fun getCourseLessons(courseId: Int)

    suspend fun getLessonUrls(key: String)
}