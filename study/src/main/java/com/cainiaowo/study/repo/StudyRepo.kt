package com.cainiaowo.study.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.support.serverData
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.service.network.onFailure
import com.cainiaowo.service.network.onSuccess
import com.cainiaowo.study.network.BoughtRsp
import com.cainiaowo.study.network.IStudyService
import com.cainiaowo.study.network.StudiedRsp
import com.cainiaowo.study.network.StudyInfoRsp
import kotlinx.coroutines.flow.Flow

/**
 * 学习中心相关的数据管理类
 */
class StudyRepo(private val service: IStudyService) : IStudyResource {

    private val _liveStudyInfo = MutableLiveData<StudyInfoRsp>()
    private val _liveStudyList = MutableLiveData<StudiedRsp>()
    private val _liveBoughtList = MutableLiveData<BoughtRsp>()

    override val liveStudyInfo: LiveData<StudyInfoRsp> = _liveStudyInfo
    override val liveStudyList: LiveData<StudiedRsp> = _liveStudyList
    override val liveBoughtList: LiveData<BoughtRsp> = _liveBoughtList

    override suspend fun getStudyInfo() {
        service.getStudyInfo()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取学习情况 BizError $code,$message")
                    return@onBizError
                }.onBizOK<StudyInfoRsp> { code, data, message ->
                    _liveStudyInfo.value = data
                    LogUtils.i("获取学习情况 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                LogUtils.e("获取学习情况 接口异常 ${it.message}")
            }
    }

    override suspend fun getStudyList() {
        service.getStudyList()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取最近学习 BizError $code,$message")
                    return@onBizError
                }.onBizOK<StudiedRsp> { code, data, message ->
                    _liveStudyList.value = data?.apply {
                        datas?.forEach {
                            // 由于服务端接口返回数据的img_url可能缺少https:协议,所以需要处理
                            it.getDetailImgUrl()
                        }
                    }
                    LogUtils.i("获取最近学习 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                LogUtils.e("获取最近学习 接口异常 ${it.message}")
            }
    }

    override suspend fun getBoughtCourse() {
        service.getBoughtCourse()
            .serverData()
            .onSuccess {
                onBizError { code, message ->
                    LogUtils.w("获取我的课程 BizError $code,$message")
                    return@onBizError
                }.onBizOK<BoughtRsp> { code, data, message ->
                    _liveBoughtList.value = data
                    LogUtils.i("获取我的课程 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                LogUtils.e("获取我的课程 接口异常 ${it.message}")
            }
    }

    /*
    * 将studyPageSource转化为flow数据
    * */
    private val pageSize = 100
    override suspend fun pagingData(): Flow<PagingData<StudiedRsp.Data>> {
        val config = PagingConfig(
            pageSize = pageSize, // 每页显示的数据的大小。对应 PagingSource 里 LoadParams.loadSize
            prefetchDistance = 5, // 预刷新的距离，距离最后一个 item 多远时加载数据，默认为 pageSize
            initialLoadSize = 10,  // 初始化加载数量，默认为 pageSize * 3
            maxSize = pageSize * 3 // 一次应在内存中保存的最大数据，默认为 Int.MAX_VALUE
        )
        // 数据源，要求返回的是 PagingSource 类型对象
        return Pager(config = config, null) {//为null 刚开始加载 0
            StudyItemPagingSource(service)
        }.flow // 最后构造的和外部交互对象，有 flow 和 liveData 两种
    }


    /*
* 处理分页逻辑
* */
    class StudyItemPagingSource(val service: IStudyService) : PagingSource<Int, StudiedRsp.Data>() {
        /*
        * 该办法只在初始加载成功且加载页面的列表不为空的情况下被调用
        *
        * 如果您的应用程序需要支持从网络增量加载到本地数据库，则必须为从用户的滚动位置锚点开始的恢复分页提供支持
        * 先从本地数据库加载数据，然后在数据库用完数据后从网络加载数据
        * */
       // override fun getRefreshKey(state: PagingState<Int, StudiedRsp.Data>): Int? = null

        //用这个方法来触发异步加载  . params 对应的是什么的config
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StudiedRsp.Data> {
            var result: LoadResult<Int, StudiedRsp.Data> =
                LoadResult.Error(Exception("加载中..."))
            var firstPage = params.key ?: 1
            service.getStudyList(firstPage, params.loadSize) //初始化加载数量
                .serverData()
                .onSuccess {
                    onBizError { code, message ->
                        LogUtils.w("获取学习过的课程列表 BizError $code,$message")
                        result = LoadResult.Error(Exception(message))
                    }
                    onBizOK<StudiedRsp> { code, data, message ->
                        // LogUtils.i("获取学习过的课程列表 BizOK $data")
                        val totalPage = data?.total_page ?: 0 //total页码
                        //加载下一页的key，如果传null就说明到底了
                        val nextPage = if (firstPage <= totalPage) {
                            // Log.d("yyy","课程列表还没到底")
                            firstPage++
                        } else {
                            // Log.d("yyy","课程列表到底啦")
                            null
                        }
                        result = LoadResult.Page<Int, StudiedRsp.Data>(
                            data?.datas ?: emptyList(), //如果datas为空就回传一个空的只读列表
                            null,
                            nextPage
                        )
                    }
                }
                .onFailure {
                    LogUtils.e("获取学习过的课程列表 接口异常 ${it.message}")
                    result = LoadResult.Error(it)
                }
            return result
        }

    }

}