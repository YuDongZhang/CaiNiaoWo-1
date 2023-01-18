package com.cainiaowo.video.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.livedata.SingleLiveData
import com.cainiaowo.common.network.support.serverData
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.service.network.onFailure
import com.cainiaowo.service.network.onSuccess
import com.cainiaowo.video.network.CourseLessonsRsp
import com.cainiaowo.video.network.CoursePermissionRsp
import com.cainiaowo.video.network.IVideoService
import com.cainiaowo.video.network.LessonUrlsRsp

class VideoRepo(private val service: IVideoService) : IVideoResource {

    private val _liveCoursePermission = MutableLiveData<CoursePermissionRsp>()

    override val liveCoursePermission: LiveData<CoursePermissionRsp> = _liveCoursePermission
    override val liveCourseLessons: SingleLiveData<CourseLessonsRsp> = SingleLiveData()
    override val liveLessonUrls: SingleLiveData<LessonUrlsRsp> = SingleLiveData()

    override suspend fun getCoursePermission(courseId: Int) {
        service.getCoursePermission(courseId)
            .serverData()
            .onSuccess {
                onBizOK<CoursePermissionRsp> { _, data, _ ->
                    _liveCoursePermission.value = data
                    LogUtils.i("获取课程学习权限 BizOK $data")
                    return@onBizOK
                }.onBizError { code, message ->
                    ToastUtils.showShort(message)
                    LogUtils.w("获取课程学习权限 BizError $code,$message")
                    return@onBizError
                }
            }
            .onFailure {
                LogUtils.e("获取课程学习权限 接口异常 ${it.message}")
            }
    }

    override suspend fun getCourseLessons(courseId: Int) {
        service.getCourseLessons(courseId)
            .serverData()
            .onSuccess {
                onBizOK<CourseLessonsRsp> { _, data, _ ->
                    liveCourseLessons.value = data
                    LogUtils.i("获取课程章节列表 BizOK $data")
                    return@onBizOK
                }.onBizError { code, message ->
                    ToastUtils.showShort(message)
                    LogUtils.w("获取课程章节列表 BizError $code,$message")
                    return@onBizError
                }
            }
            .onFailure {
                LogUtils.e("获取课程章节列表 接口异常 ${it.message}")
            }
    }

    override suspend fun getLessonUrls(key: String) {
        service.getLessonUrls(key)
            .serverData()
            .onSuccess {
                onBizOK<LessonUrlsRsp> { _, data, _ ->
                    liveLessonUrls.value = data
                    LogUtils.i("获取课时播放地址 BizOK $data")
                    return@onBizOK
                }.onBizError { code, message ->
                    ToastUtils.showShort(message)
                    LogUtils.w("获取课时播放地址 BizError $code,$message")
                    return@onBizError
                }
            }.onFailure {
                LogUtils.e("获取课时播放地址 接口异常 ${it.message}")
            }
    }
}