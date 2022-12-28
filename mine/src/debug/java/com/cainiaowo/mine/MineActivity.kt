package com.cainiaowo.mine

import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.mine.databinding.ActivityMineBinding

/**
 * 调试用的 mine 模块入口Activity
 */
class MineActivity : BaseActivity<ActivityMineBinding>() {

    override fun getLayoutRes() = R.layout.activity_mine

    override fun initView() {
        super.initView()
        mBinding.apply {

        }
    }

}