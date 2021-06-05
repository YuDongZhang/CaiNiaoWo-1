package com.cainiaowo.common.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.cainiaowo.common.ktx.viewLifecycleOwner

/**
 * Activity抽象基类
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 扩展LiveData的observe函数
     */
    protected fun <T : Any> LiveData<T>.observe(block: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            block.invoke(data)
        }
    }
}