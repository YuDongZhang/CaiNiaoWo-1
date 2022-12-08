package com.cainiaowo.common.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

/**
 * Fragment抽象基类
 */
abstract class BaseFragment : Fragment {  //Fragment() 可以写带括号 带构造的
    //无参构造函数 调用无参
    constructor() : super()

    //有参构造函数
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