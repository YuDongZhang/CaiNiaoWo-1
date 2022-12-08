package com.cainiaowo.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.databinding.FragmentMineBinding

/**
 * 我的
 */
class MineFragment : BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentMineBinding.bind(view)
    }
}