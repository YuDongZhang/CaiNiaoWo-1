package com.cainiaowo.video.viewmodel

import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.video.repo.IVideoResource

class VideoViewModel(private val resource: IVideoResource) : BaseViewModel() {

    val liveCoursePermission = resource.liveCoursePermission
    val liveCourseLessons = resource.liveCourseLessons
    val liveLessonUrls = resource.liveLessonUrls

    fun getCoursePermission(courseId: Int) = serveAwait {
        resource.getCoursePermission(courseId)
    }

    fun getCourseLessons(courseId: Int) = serveAwait {
        resource.getCourseLessons(courseId)
    }

    fun getLessonUrls(key: String) = serveAwait {
        resource.getLessonUrls(key)
    }
}