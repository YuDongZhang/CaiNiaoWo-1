package com.cainiaowo.study.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.model.onFailure
import com.cainiaowo.common.network.model.onSuccess
import com.cainiaowo.common.network.support.serveData
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.study.network.BoughtRsp
import com.cainiaowo.study.network.IStudyService
import com.cainiaowo.study.network.StudiedRsp
import com.cainiaowo.study.network.StudyInfoRsp

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
            .serveData()
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

    override suspend fun getStudyList(page: Int, size: Int) {
        service.getStudyList(page, size)
            .serveData()
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

    override suspend fun getBoughtCourse(page: Int, size: Int) {
        service.getBoughtCourse(page, size)
            .serveData()
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
}