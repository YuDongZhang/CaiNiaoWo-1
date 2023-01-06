package com.cainiaowo.common.utils

import com.cainiaowo.common.BuildConfig

/**
 * Baseurl配置,与dokit搭配使用
 */

/**
 * 根据实际开发配置不同的Host
 */
const val HOST_DEV = "https://course.api.cniao5.com/"   // 开发环境
const val HOST_RELEASE = "https://release.course.api.cniao5.com/"     // 正式环境,当前仅作模拟
// ...其它环境

/**
 * Key-Value中的Key
 */
private const val SP_KEY_BASE_HOST = "sp_key_base_host"

/**
 * 获取Host环境
 */
fun getBaseHost(): String {
    return if (BuildConfig.DEBUG) {
        MMKVUtils.getString(SP_KEY_BASE_HOST) ?: HOST_DEV
    } else {
        HOST_RELEASE
    }
}

/**
 * 设置Host环境
 */
fun setBaseHost(host: String) {
    MMKVUtils.put(SP_KEY_BASE_HOST, host)
}