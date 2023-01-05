package com.cainiaowo.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.databinding.FragmentMineBinding
import com.cainiaowo.service.repo.CaiNiaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 我的
 */
class MineFragment : BaseFragment() {

    private val viewModel: MineViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentMineBinding.bind(view).apply {
            vm = viewModel

            // UI登出操作
            btnLogoutMine.setOnClickListener {
                CaiNiaoDbHelper.deleteUserInfo(requireContext())
                ARouter.getInstance().build("/login/login").navigation()
            }
        }
    }

    override fun initData() {
        super.initData()
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observerKt {
            viewModel.liveUser.value = it
        }
    }
}