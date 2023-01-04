package com.cainiaowo.login.net


import androidx.annotation.Keep
import com.cainiaowo.service.repo.CaiNiaoUserInfo

/**
 * 查询手机号码是否注册 接口响应
 */
@Keep
data class RegisterRsp(
    val is_register: Int = FLAG_UNREGISTERED    // 1表示已注册,0表示未注册
) {
    companion object {
        const val FLAG_REGISTERED = 1   // 已注册
        const val FLAG_UNREGISTERED = 0 // 未注册
    }
}

/**
 * 手机号和密码登录 接口响应
 */
typealias LoginRsp = CaiNiaoUserInfo