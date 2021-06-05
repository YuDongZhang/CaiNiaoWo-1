package com.cainiaowo.common.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * Fragment抽象基类
 */
abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    /**
     * 扩展LiveData的observe函数
     */
    protected fun <T : Any> LiveData<T>.observe(block: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            block.invoke(data)
        }
    }
}