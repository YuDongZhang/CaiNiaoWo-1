package com.cainiaowo.viewmodeldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider

class ViewModelActivity : AppCompatActivity() {

    /**
     * 创建无参ViewModel方式一
     */
    val noParamViewModel1 by ViewModelLazy(
        NoParamViewModel::class,
        { viewModelStore },
        { defaultViewModelProviderFactory })

    /**
     * 创建无参ViewModel方式二:
     * 本质上就是使用了方式一
     * 在Activity中使用需要在build.gradle中添加androidx.activity:activity-ktx依赖
     */
    val noParamViewModel2 by viewModels<NoParamViewModel> { defaultViewModelProviderFactory }

    /**
     * 有参ViewModel和无参ViewModel创建类似
     */
    val paramViewModel1 by ViewModelLazy(
        ParamViewModel::class,
        { viewModelStore },
        { ParamFactory(application) })

    val paramViewModel2 by viewModels<ParamViewModel> { ParamFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        /**
         * 创建无参ViewModel方式三
         */
        val noParamViewModel3 = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(NoParamViewModel::class.java)

        /**
         * 有参ViewModel
         */
        val paramViewModel3 = ViewModelProvider(
            viewModelStore,
            ParamFactory(application)
        ).get(ParamViewModel::class.java)
    }
}