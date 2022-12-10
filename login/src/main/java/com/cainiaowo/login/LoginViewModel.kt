package com.cainiaowo.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.ToastUtils

/**
 * 登录界面逻辑的viewModel
 */
class LoginViewModel : ViewModel() {

    /**
     * 账号,密码的observable对象
     */
    val obMobile = ObservableField<String>()
    val obPassword = ObservableField<String>()

    fun goLogin() {

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