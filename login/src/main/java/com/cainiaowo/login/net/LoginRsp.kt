package com.cainiaowo.login.net


import androidx.annotation.Keep

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
@Keep
data class LoginRsp(
    val course_permission: Boolean,     // 是否拥有该课程的学习权限
    val token: String?,                 // 登录凭证，与用户相关的接口都需要传该token值。（PS:没有课程学习权限的用户不返回该字段）
    val user: User?                     // 用户信息
) {
    @Keep
    data class User(
        val id: Int,                  // 用户id
        val is_bind_phone: Boolean,  // 是否已经绑定手机
        val logo_url: String?,      // 用户头像
        val username: String?       // 用户名
    )
}
