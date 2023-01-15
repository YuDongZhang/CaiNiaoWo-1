package com.cainiaowo.course.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.course.R
import com.cainiaowo.course.databinding.FragmentCourseBinding
import com.cainiaowo.course.viewmodel.CourseViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 课程
 */
class CourseFragment : BaseFragment() {

    private val viewModel: CourseViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_course
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        //also 下面可以拿到databing引用 , 从下面it 可以理解就是 databinding  , it一般就是指上面这一块
        return FragmentCourseBinding.bind(view).apply {
            vm = viewModel
        }
    }


    override fun initData() {
        super.initData()
        viewModel.getCourseCategory()
        //获取课程类型下的数据列表
        viewModel.apply {
            adapter.addLoadStateListener {
                when (it.refresh) {
                    is LoadState.NotLoading -> {

                    }
                    is LoadState.Loading -> {

                    }
                    is LoadState.Error -> {

                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                //这是课堂视频版本的
//                typedCourseList().observerKt {
//                    it?.let {
//                        adapter.submitData(lifecycle,it)
//                    }
//                }
                //这个是 master 原版的
                viewModel.getCourseList().collectLatest { pagingData ->
                    viewModel.adapter.submitData(pagingData)
                }

            }
        }
    }
}