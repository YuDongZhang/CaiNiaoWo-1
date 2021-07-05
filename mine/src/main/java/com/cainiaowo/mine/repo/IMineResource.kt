package com.cainiaowo.mine.repo

import androidx.lifecycle.LiveData
import com.cainiaowo.mine.network.UserInfoRsp

/**
 * 我的模块相关的抽象数据接口
 */
interface IMineResource {

    /**
     * UserInfo数据
     */
    val liveUserInfo: LiveData<UserInfoRsp>

    /**
     * 获取UserInfo
     */
    suspend fun getUserInfo(token: String?)

}