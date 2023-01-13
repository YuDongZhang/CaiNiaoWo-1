package com.cainiaowo.study.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.service.repo.CaiNiaoDbHelper
import com.cainiaowo.study.R
import com.cainiaowo.study.databinding.FragmentStudyBinding
import com.cainiaowo.study.databinding.FragmentStudyDetailBinding
import com.cainiaowo.study.viewmodel.StudyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 学习中心
 */
class StudyFragment : BaseFragment() {

    private val viewModel: StudyViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_study

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentStudyBinding.bind(view).apply {
            vm = viewModel
        }
    }


    override fun initData() {
        super.initData()
        //观察数据库中的 userinfo
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observerKt {
            viewModel.obUserInfo.set(it)
            // 用户没有登录过时不去获取信息
            it?.let {
                viewModel.getStudyInfo()
            }
        }
        //获取最近学习中的数据列表
        viewModel.apply {
            liveStudyList.observerKt {
               // adapter.submit(it?.data?:emptylist()) 老的接口
            }
            lifecycleScope.launchWhenCreated {
                pagingData().observerKt {
                    it?.let {
                        adapter.submitData(lifecycle,it)
                    }
                }
            }
        }
    }
}


