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

//    val catchEX = CoroutineExceptionHandler { coroutineContext, throwable ->
//        LogUtils.e("异常${throwable.message}") }

    /** 调用登录2步 , 1. 判断手机号是否已经注册 ---*/
    fun goLogin() {
//        viewModelScope.launch(catchEX) {
//            resource.checkRegister("18648957777")
//            resource.requestLogin(LoginReqBody("18648957777","cn5123456"))
//        }
        //封装之后可以这样写
        serveAwait {
            resource.checkRegister("18648957777")
            resource.requestLogin(LoginReqBody("18648957777", "cn5123456"))
        }
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