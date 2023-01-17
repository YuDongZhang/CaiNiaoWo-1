package com.cainiaowo.home.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.common.network.model.DataResult
import com.cainiaowo.common.network.onBizOK
import com.cainiaowo.home.R
import com.cainiaowo.home.adapter.BannerAdapter
import com.cainiaowo.home.adapter.HomeAdapter
import com.cainiaowo.home.adapter.HomeItem
import com.cainiaowo.home.databinding.FragmentHomeBinding
import com.cainiaowo.home.network.BannerItem
import com.cainiaowo.home.network.JobCourseListRsp
import com.cainiaowo.home.network.NormalCourseListRsp
import com.cainiaowo.home.network.PopTeacherListRsp
import com.cainiaowo.home.utils.*
import com.cainiaowo.home.utils.TYPE_ANDROID_COURSE
import com.cainiaowo.home.utils.TYPE_JOB_COURSE
import com.cainiaowo.home.utils.TYPE_LIMIT_FREE_COURSE
import com.cainiaowo.home.utils.TYPE_NEW_COURSE
import com.cainiaowo.home.viewmodel.HomeViewModel
import com.cainiaowo.service.network.BaseCaiNiaoRsp
import com.cainiaowo.service.network.onBizOK
import com.cainiaowo.service.network.onFailure
import com.cainiaowo.service.network.onSuccess
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 首页
 */
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val mBanners = mutableListOf<BannerItem>()
    private val bannerAdapter by lazy {
        BannerAdapter(mBanners)
    }

    private val homeList = mutableListOf<HomeItem>()
    private val homeAdapter = HomeAdapter(homeList)

    private lateinit var mBinding: FragmentHomeBinding

    override fun getLayoutRes() = R.layout.fragment_home
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        mBinding = FragmentHomeBinding.bind(view)
        return mBinding
    }


    override fun initConfig() {
        super.initConfig()
        mBinding.apply {
            vm = viewModel
            adapter = homeAdapter

            banner.addBannerLifecycleObserver(viewLifecycleOwner)
                .setAdapter(bannerAdapter).indicator = CircleIndicator(requireContext())
        }
    }

    override fun initData() {
        super.initData()
        viewModel.apply {
            getBannerList()

            getPageModuleList()

            liveBannerList.observerKt { banners ->
                banners ?: return@observerKt
                mBanners.clear()
                mBanners.addAll(banners)
                bannerAdapter.notifyDataSetChanged()
            }

            // 观察首页模块的分类
            val scope = CoroutineScope(SupervisorJob())
            livePageModuleList.observerKt { pageModules ->
                lifecycleScope.launchWhenCreated {
                    pageModules?.map { moduleItem ->
                        moduleItem.id to scope.async { getPageModuleItems(moduleItem.id) }  // 根据id获取内容
                    }?.asFlow()?.collect { result ->
                        parseResult(result.first, result.second.await())
                    }
                    homeAdapter.setList(homeList)
                }
            }

        }
    }

    private fun parseResult(id: Int, data: DataResult<BaseCaiNiaoRsp>) {
        data.onSuccess {
            when (id) {
                TYPE_JOB_COURSE -> {
                    onBizOK<JobCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "就业班", data))
                    }
                }
                TYPE_NEW_COURSE -> {
                    onBizOK<NormalCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "新上好课", data))
                    }
                }
                TYPE_LIMIT_FREE_COURSE -> {
                    onBizOK<NormalCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "限时免费", data))
                    }
                }
                TYPE_ANDROID_COURSE -> {
                    onBizOK<NormalCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "Android精选", data))
                    }
                }
                TYPE_AI_COURSE -> {
                    onBizOK<NormalCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "AI精选", data))
                    }
                }
                TYPE_BD_COURSE -> {
                    onBizOK<NormalCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "大数据精选", data))
                    }
                }
                TYPE_SUGGEST_COURSE -> {
                    onBizOK<NormalCourseListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "学员推荐", data))
                    }
                }
                TYPE_TEACHER_COURSE -> {
                    onBizOK<PopTeacherListRsp> { _, data, _ ->
                        homeList.add(HomeItem(id, "人气讲师", data))
                    }
                }
            }

        }.onFailure {
            LogUtils.e("获取主页各个分类数据 接口异常 ${it.message}")
        }
    }

}