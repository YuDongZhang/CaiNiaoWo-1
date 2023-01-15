package com.cainiaowo.course.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.course.network.CourseCategoryRsp
import com.cainiaowo.course.network.CourseListRsp
import com.cainiaowo.course.paging.CoursePagerAdapter
import com.cainiaowo.course.repo.ICourseResource
import kotlinx.coroutines.flow.Flow

class CourseViewModel(private val resource: ICourseResource) : BaseViewModel() {

    val liveCourseCategory: LiveData<CourseCategoryRsp> = resource.liveCourseCategory

    fun getCourseCategory() = serveAwait {
        resource.getCourseCategory()
    }

    val adapter = CoursePagerAdapter()

    /**
     * [courseType] 类型 (-1 全部) (1 普通课程) (2 职业课程/班级课程) (3 实战课程) 默认 -1
     * [code] 课程关于哪个方面 从课程分类接口获取 默认 "all" ,例如android等
     * [difficulty] 难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
     * [isFree] 价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
     * [q] 排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
     */
    //这里是master的
    suspend fun getCourseList(
        courseType: Int = -1,
        code: String = "all",
        difficulty: Int = -1,
        isFree: Int = -1,
        q: Int = -1
    ): Flow<PagingData<CourseListRsp.Course>> {
        // cachedIn是将数据在ViewModelScope作用域内缓存
        return resource.getCourseList(courseType, code, difficulty, isFree, q)
            .cachedIn(viewModelScope)
    }

    //这里是课程的
    suspend fun typedCourseList() =
        resource.getCourseList( -1,
    "all",
     -1,
    -1,
    -1)
            .asLiveData(viewModelScope.coroutineContext)
           //关联对应的协程的上下文的环境
            .cachedIn(viewModelScope)
            //把协程和viewmodel缓存关联起来

}