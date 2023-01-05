package com.cainiaowo.mine.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.R
import com.cainiaowo.mine.databinding.FragmentUserInfoBinding
import com.cainiaowo.service.repo.CaiNiaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 用户信息界面
 */
class UserInfoFragment : BaseFragment() {

    private val viewModel: MineViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_user_info

    override fun bindView(view: View, savedInstanceState: Bundle?) =
        FragmentUserInfoBinding.bind(view).apply {
            vm = viewModel

            // Toolbar返回
            toolbarUserInfo.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            // Button返回
            btnSaveUserInfo.setOnClickListener {
                findNavController().navigateUp()
            }
        }

    override fun initData() {
        super.initData()
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observerKt {
            // 获取UserInfo接口数据

        }
    }
}