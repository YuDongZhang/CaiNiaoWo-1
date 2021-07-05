package com.cainiaowo.mine.ui

import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.mine.repo.IMineResource

/**
 * 我的界面逻辑的viewModel
 */
class MineViewModel(private val resource: IMineResource) : BaseViewModel() {

    /**
     * UserInfoFragment中使用
     */
    val liveUserInfo = resource.liveUserInfo

    /**
     * 获取UserInfo
     */
    fun getUserInfo(token: String?) {
        serveAwait {
            resource.getUserInfo(token)
        }
    }
}