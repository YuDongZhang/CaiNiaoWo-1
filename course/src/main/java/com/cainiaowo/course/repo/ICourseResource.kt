package com.cainiaowo.course.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.cainiaowo.course.network.CourseCategoryRsp
import com.cainiaowo.course.network.CourseListRsp
import kotlinx.coroutines.flow.Flow

interface ICourseResource {

    val liveCourseCategory: LiveData<CourseCategoryRsp>

    /**
     * 课程分类
     */
    suspend fun getCourseCategory()

    /**
     * 课程列表,使用Flow+Paging3
     */
    suspend fun getCourseList(
        courseType: Int,
        code: String,
        difficulty: Int,
        isFree: Int,
        q: Int
    ): Flow<PagingData<CourseListRsp.Course>>

}