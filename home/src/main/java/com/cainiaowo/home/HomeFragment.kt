package com.cainiaowo.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.home.databinding.FragmentHomeBinding

/**
 * 首页
 */
class HomeFragment : BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentHomeBinding.bind(view)
    }
}