package com.cainiaowo.login.network

import androidx.annotation.Keep
//这个是什么 ? 这个就是请求参数的类 , 现在是封装为类
//其实就是 LoginRequestBody
@Keep
data class LoginReqBody(
    val mobi: String,
    val password: String
)