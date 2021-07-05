package com.cainiaowo.mine.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.model.onFailure
import com.cainiaowo.common.network.model.onSuccess
import com.cainiaowo.common.network.support.serveData
import com.cainiaowo.mine.network.IMineService
import com.cainiaowo.mine.network.UserInfoRsp
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK

/**
 * 我的模块相关的数据管理类
 */
class MineRepo(private val service: IMineService) : IMineResource {

    private val _liveUserInfo = MutableLiveData<UserInfoRsp>()

    override val liveUserInfo: LiveData<UserInfoRsp> = _liveUserInfo

    override suspend fun getUserInfo(token: String?) {
        service.getUserInfo(token)
            .serveData()
            .onSuccess {
                // 接口响应成功
                onBizError { code, message ->
                    LogUtils.w("获取用户信息 BizError $code,$message")
                }.onBizOK<UserInfoRsp> { code, data, message ->
                    _liveUserInfo.value = data
                    LogUtils.i("获取用户信息 BizOK $data")
                    return@onBizOK
                }
            }.onFailure {
                // 接口响应失败
                LogUtils.e("获取用户信息接口异常 ${it.message}")
            }
    }

}