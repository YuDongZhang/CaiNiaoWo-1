package com.cainiaowo.course.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.support.serverData
import com.cainiaowo.course.network.CourseCategoryRsp
import com.cainiaowo.course.network.CourseListRsp
import com.cainiaowo.course.network.ICourseService
import com.cainiaowo.course.paging.CoursePagingSource
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.service.network.onFailure
import com.cainiaowo.service.network.onSuccess
import kotlinx.coroutines.flow.Flow

class CourseRepo(private val service: ICourseService) : ICourseResource {

    private val _liveCourseCategory = MutableLiveData<CourseCategoryRsp>()

    override val liveCourseCategory: LiveData<CourseCategoryRsp> = _liveCourseCategory

    override suspend fun getCourseCategory() {
        service.getCourseCategory()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取课程分类 BizError $code,$message")
                    return@onBizError
                }.onBizOK<CourseCategoryRsp> { code, data, message ->
                    _liveCourseCategory.value = data
                    LogUtils.i("获取课程分类 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                LogUtils.e("获取课程分类 接口异常 ${it.message}")
            }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    /**
     * 使用Paging3,返回值固定是Flow<PaingData<T>>,其中T是每一项数据对应的类型
     */
    override suspend fun getCourseList(
        courseType: Int,
        code: String,
        difficulty: Int,
        isFree: Int,
        q: Int
    ): Flow<PagingData<CourseListRsp.Course>> {
        val pagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,   // 每页包含的数据
            prefetchDistance = 5    // 预加载时距离下页的距离
        )
        // initialKey:初始页页码
        return Pager(pagingConfig, null) {
            CoursePagingSource(service, courseType, code, difficulty, isFree, q)
        }.flow
    }
}