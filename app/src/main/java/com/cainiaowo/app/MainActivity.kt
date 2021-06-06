package com.cainiaowo.app

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cainiaowo.app.databinding.ActivityMainBinding
import com.cainiaowo.common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun initView() {
        super.initView()
        val navController = findNavController(R.id.fcv_main)
        mBinding.bnvMain.setupWithNavController(navController)
    }

    override fun initConfig() {
        super.initConfig()
    }

    override fun initData() {
        super.initData()
    }
}