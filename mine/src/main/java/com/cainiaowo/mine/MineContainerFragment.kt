package com.cainiaowo.mine

import android.os.Bundle
import android.view.View
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.databinding.FragmentContainerMineBinding

/**
 * Mine模块Fragment,navigation的容器
 */
class MineContainerFragment : BaseFragment() {

    override fun getLayoutRes() = R.layout.fragment_container_mine

    override fun bindView(view: View, savedInstanceState: Bundle?) =
        FragmentContainerMineBinding.bind(view)

}