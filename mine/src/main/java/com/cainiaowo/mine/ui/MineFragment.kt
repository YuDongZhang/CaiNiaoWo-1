package com.cainiaowo.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.R
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

            // 使用navigation跳转到UserInfoFragment
            ivUserIconMine.setOnClickListener {
                val action = MineFragmentDirections.actionMineFragmentToUserInfoFragment()
                findNavController().navigate(action)
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