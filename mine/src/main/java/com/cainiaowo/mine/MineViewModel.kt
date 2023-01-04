package com.cainiaowo.mine

import androidx.lifecycle.MutableLiveData
import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.service.repo.CaiNiaoUserInfo

/**
 * 我的界面逻辑的viewModel
 */
class MineViewModel : BaseViewModel() {

    val liveUser = MutableLiveData<CaiNiaoUserInfo>()

}