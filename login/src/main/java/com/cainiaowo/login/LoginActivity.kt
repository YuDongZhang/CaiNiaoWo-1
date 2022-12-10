package com.cainiaowo.login

import androidx.activity.viewModels
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.login.databinding.ActivityLoginBinding

/**
 * 登录界面
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels { defaultViewModelProviderFactory }

    override fun getLayoutRes() = R.layout.activity_login

    override fun initView() {
        super.initView()
        mBinding.apply {
            vm = viewModel
            // 点击事件
            toolbarLogin.setNavigationOnClickListener { finish() }
            tvRegisterLogin.setOnClickListener { ToastUtils.showShort("点击了注册按钮,后端没有对应接口,只显示UI")}
        }
    }

    override fun initConfig() {
        super.initConfig()
    }

    override fun initData() {
        super.initData()
    }
}