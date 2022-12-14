package com.cainiaowo.login

import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.login.databinding.ActivityLoginBinding
import com.cainiaowo.login.net.RegisterRsp
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 登录界面 , 请求是在ui层通过databinding 绑定 viewmodel
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

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
        viewModel.apply {

            liveRegisterRsp.observerKt {
                if (it?.is_register == RegisterRsp.FLAG_REGISTERED) {
                    repoLogin()
                }
            }
            liveLoginRsp.observerKt {
                ToastUtils.showShort("登陆结果 " + it.toString())
            }
        }
    }

    override fun initData() {
        super.initData()
    }
}