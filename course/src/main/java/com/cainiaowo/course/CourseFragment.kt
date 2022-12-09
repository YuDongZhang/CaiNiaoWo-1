package com.cainiaowo.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        //also 下面可以拿到databing引用 , 从下面it 可以理解就是 databinding  , it一般就是指上面这一块
        return FragmentCourseBinding.bind(view).also {
            it.tvTitleFragment
        }
    }


    /*private var mBinding: FragmentCourseBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCourseBinding.bind(view)
        mBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCourseBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }
*/


    /**
     * 这个界面的代码  放到继承类里面
     */
    /*

       override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         val binding = FragmentCourseBinding.inflate(inflater,container,false)
         return binding.root
     }


     class CourseFragment : BaseFragment(R.layout.fragment_course) {

         private var mBinding: FragmentCourseBinding? = null

         override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
             super.onViewCreated(view, savedInstanceState)
             mBinding = FragmentCourseBinding.bind(view)
             mBinding?.apply {
                 lifecycleOwner = viewLifecycleOwner
             }
         }

         override fun onDestroy() {
             super.onDestroy()
             mBinding?.unbind()
         }
 */

}