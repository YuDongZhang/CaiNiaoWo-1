package com.cainiaowo.login.net

import androidx.annotation.Keep

@Keep
data class LoginReqBody(
    val mobi: String,//手机号
    val password: String
)