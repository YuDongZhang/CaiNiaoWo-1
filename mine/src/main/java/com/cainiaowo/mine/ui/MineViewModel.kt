package com.cainiaowo.mine.ui

import androidx.lifecycle.MutableLiveData
import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.mine.network.UserInfoRsp
import com.cainiaowo.service.repo.CaiNiaoUserInfo

/**
 * 我的界面逻辑的viewModel
 */
class MineViewModel : BaseViewModel() {

    /**
     * MineFragment中使用
     */
    val liveUser = MutableLiveData<CaiNiaoUserInfo>()

    /**
     * UserInfoFragment中使用
     */
    val liveInfo = MutableLiveData<UserInfoRsp>()
}