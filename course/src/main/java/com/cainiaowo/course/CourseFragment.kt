package com.cainiaowo.course

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.course.databinding.FragmentCourseBinding

/**
 * 课程
 */
class CourseFragment : BaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_course
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentCourseBinding.bind(view)
    }

}