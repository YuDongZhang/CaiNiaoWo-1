package com.cainiaowo.study.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
class StudyFragment : BaseFragment<FragmentStudyBinding>() {

    private val viewModel: StudyViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_study

    private val fragments = mapOf<Int, Fragment>(
        0 to StudyListFragment(),
        1 to BoughtCourseFragment()
    )

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mBinding.apply {
            vm = viewModel

            // ViewPager2适配器
            viewPager2.adapter = StudyViewPager2Adapter(this@StudyFragment, fragments)

            viewPager2.isUserInputEnabled = false

            TabLayoutMediator(tlStudy, viewPager2) { tab, position ->
                when (position) {
                    0 -> tab.apply {
                        text = "最近学习"
                    }
                    1 -> tab.apply {
                        text = "我的课程"
                    }
                }
            }.attach()
        }
    }

    override fun initData() {
        super.initData()
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observe {
            viewModel.obUserInfo.set(it)
            // 用户没有登录过时不去获取信息
            it?.let {
                viewModel.getStudyInfo()
            }
        }
    }
}

/**
 * 学习中心下面部分的ViewPager2适配器
 */
class StudyViewPager2Adapter(
    fragment: Fragment,
    private val fragments: Map<Int, Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment =
        fragments[position] ?: error("请确保fragments数据源和viewPager2的index匹配设置")
}


/**
 * 最近学习
 */
class StudyListFragment : BaseFragment<FragmentStudyDetailBinding>() {

    private val viewModel: StudyViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_study_detail

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mBinding.recyclerView.adapter = viewModel.studiedAdapter
    }

    override fun initData() {
        super.initData()
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observe {
            if (it == null) {
                // 用户没有登录过时不去获取信息,并且显示暂无数据
                mBinding.apply {
                    recyclerView.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }
            } else {
                // 用户已登录,去获取数据
                viewModel.getStudyList()
            }
        }
        // 观察数据返回结果
        viewModel.liveStudyList.observe { studiedRsp ->
            if (studiedRsp?.datas == null || studiedRsp.datas.isEmpty()) {
                // 没有CourseInfo数据,不显示RecyclerView
                mBinding.apply {
                    recyclerView.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }
            } else {
                viewModel.studiedAdapter.setNewInstance(studiedRsp.datas.toMutableList())
                mBinding.apply {
                    recyclerView.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE
                }
            }
        }
    }
}

/**
 * 我的课程
 */
class BoughtCourseFragment : BaseFragment<FragmentStudyDetailBinding>() {

    private val viewModel: StudyViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_study_detail

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mBinding.recyclerView.adapter = viewModel.boughtCourseAdapter
    }

    override fun initData() {
        super.initData()
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observe {
            if (it == null) {
                // 用户没有登录过时不去获取信息,并且显示暂无数据
                mBinding.apply {
                    recyclerView.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }
            } else {
                // 用户已登录,去获取数据
                viewModel.getBoughtCourse()
            }
        }
        // 观察数据返回结果
        viewModel.liveBoughtList.observe { boughtRsp ->
            if (boughtRsp?.datas == null || boughtRsp.datas.isEmpty()) {
                mBinding.apply {
                    recyclerView.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }
            } else {
                viewModel.boughtCourseAdapter.setNewInstance(boughtRsp.datas.toMutableList())
                mBinding.apply {
                    recyclerView.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE
                }
            }
        }
    }
}