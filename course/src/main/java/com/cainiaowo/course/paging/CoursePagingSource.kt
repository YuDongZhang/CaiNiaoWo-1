package com.cainiaowo.course.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.support.serverData
import com.cainiaowo.course.network.CourseListRsp
import com.cainiaowo.course.network.ICourseService
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.service.network.onFailure
import com.cainiaowo.service.network.onSuccess

/**
 * Paging3中重要部分,提供对应当前页数的数据
 * PagingSource中的两个泛型分别表示 页数的数据类型 和 每一项数据的对象类型
 */
class CoursePagingSource(
    private val service: ICourseService,
    private val courseType: Int = -1,
    private val code: String = "all",
    private val difficulty: Int = -1,
    private val isFree: Int = -1,
    private val q: Int = -1,
) : PagingSource<Int, CourseListRsp.Course>() {

//    override fun getRefreshKey(state: PagingState<Int, CourseListRsp.Course>): Int? {
//        return null
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CourseListRsp.Course> {
        var loadResult: LoadResult<Int, CourseListRsp.Course> =
            LoadResult.Error(Throwable("加载中..."))
        try {
            // params.key：表示当前的页数,该值可以null,如果null设置成第一页
            val page = params.key ?: 1
            // params.loadSize：表示每一页包含多少条数据,在PagingConfig中配置
            val pageSize = params.loadSize
            // 进行网络请求
            service.getCourseList(courseType, code, difficulty, isFree, q, page, pageSize)
                .serverData()
                .onSuccess {
                    onBizError { code, message ->
                        LogUtils.w("获取课程列表 BizError $code,$message")
                        loadResult = LoadResult.Error(Throwable(message))
                        return@onBizError
                    }.onBizOK<CourseListRsp> { code, data, message ->
                        LogUtils.i("获取课程列表 BizOK $data")
                        // 上一页的页数
                        val prevKey = if (page > 1) page - 1 else null
                        // 下一页的页数
                        val nextKey = if (page < data?.total_page ?: 0) page + 1 else null
                        loadResult = LoadResult.Page(
                            data?.datas ?: emptyList(),
                            prevKey,
                            nextKey
                        )
                        return@onBizOK
                    }
                }.onFailure {
                    LogUtils.e("获取课程列表 接口异常 ${it.message}")
                    loadResult = LoadResult.Error(it)
                }
        } catch (e: Exception) {
            loadResult = LoadResult.Error(e)
        }
        return loadResult
    }
}