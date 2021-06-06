package com.cainiaowo.study

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.study.databinding.FragmentStudyBinding

/**
 * 学习中心
 */
class StudyFragment : BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_study
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentStudyBinding.bind(view)
    }
}