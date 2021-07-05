package com.cainiaowo.mine.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.R
import com.cainiaowo.mine.databinding.FragmentUserInfoBinding

/**
 * 用户信息界面
 */
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding>() {

    private val args by navArgs<UserInfoFragmentArgs>()

    override fun getLayoutRes() = R.layout.fragment_user_info

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mBinding.apply {

            // Toolbar返回
            toolbarUserInfo.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

//            toolbarUserInfo.navigationIcon?.setTint(Color.WHITE)

            // Button返回
            btnSaveUserInfo.setOnClickListener {
                findNavController().navigateUp()
            }

            info = args.info
        }
    }

}