package com.cainiaowo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cainiaowo.app.databinding.ActivityMainBinding
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.common.ktx.bindView

//AppCompatActivity(R.layout.activity_main) 也可以写这种
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initConfig() {
        super.initConfig()
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView<ActivityMainBinding>(R.layout.activity_main)
        initConfig()
    }

    private fun initConfig(){

    }*/
}