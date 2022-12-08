package com.cainiaowo.common.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cainiaowo.common.ktx.viewLifecycleOwner

/**
 * Activity抽象基类
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 扩展LiveData的observe函数
     */                                        //接受代码块 匿名函数
    protected fun <T : Any> LiveData<T>.observe(block: (T) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            block.invoke(data)//执行代码块
        }
    }

    //和上面写法差不多                    //上面的灰色是未调用
    protected fun <T : Any> LiveData<T>.observeKt(block: (T) -> Unit) {
        this.observe(this@BaseActivity, Observer { data ->
            block(data)                       //这灰色是可以精简
        })
    }

    ////观察值
    //        liveAppleData.observe(this, Observer {
    //            binding.tvLiveDataActivity.text = it
    //            Log.d("LiveActivity", "LiveData在LiveActivity中 $it")
    //        })
}