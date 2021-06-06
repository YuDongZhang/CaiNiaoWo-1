package com.cainiaowo.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.ktx.bindView
import com.cainiaowo.common.ktx.viewLifecycleOwner

/**
 * Activity抽象基类
 */
abstract class BaseActivity<ActivityDataBinding : ViewDataBinding> : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    protected lateinit var mBinding: ActivityDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindView<ActivityDataBinding>(getLayoutRes()).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        initView()
        initConfig()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * 初始化view
     */
    open fun initView() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initView")
    }

    /**
     * 初始化配置
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * 初始化数据
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

    /**
     * 扩展用于LiveData便捷写法的observe函数
     * [block] 响应change变化的逻辑块
     */
    protected fun <T : Any> LiveData<T>.observe(block: (T?) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            block.invoke(data)
        }
    }
}