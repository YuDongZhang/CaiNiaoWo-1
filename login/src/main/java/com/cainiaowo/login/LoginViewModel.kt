package com.cainiaowo.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.login.net.LoginReqBody
import com.cainiaowo.login.repo.ILoginResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * 登录界面逻辑的viewModel
 */
class LoginViewModel(private val resource: ILoginResource) : BaseViewModel() {

    /**
     * 账号,密码的observable对象
     */
    val obMobile = ObservableField<String>()
    val obPassword = ObservableField<String>()

    val liveRegisterRsp = resource.registerRsp
    val liveLoginRsp = resource.loginRsp

    /**
     * 判断手机号是否注册
     */
    private fun checkRegister(mobi: String) = serveAwait {
        resource.checkRegister(mobi)
    }

    /**
     * 调用登录
     */
    internal fun repoLogin() {
        val account = obMobile.get() ?: return
        val password = obPassword.get() ?: return
        serveAwait {
            resource.requestLogin(LoginReqBody(account, password))
        }
    }

    /**
     * 调用登录,两步
     * 1. 判断手机号是否注册
     * 2. 已经注册的,才去调用登录
     */
    fun goLogin() {
        val account = obMobile.get() ?: return
        checkRegister(account)
    }

    fun forget(v: View) {
        ToastUtils.showShort("静态点击方式")
    }

    fun wechat(context: Context) {
        ToastUtils.showShort("点击了微信登录")
    }

    fun qq(v: View) {
        ToastUtils.showShort("点击了QQ登录")
    }

    fun weibo() {
        ToastUtils.showShort("点击了微博登录")
    }
}