package com.cainiaowo.common.utils

import com.tencent.mmkv.MMKV

/**
 * key-value 数据存储管理类,使用MMKV
 */
object MMKVUtils {

    private val mmkv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    fun put(key: String, value: Any?) {
        when (value) {
            is Boolean -> mmkv.putBoolean(key, value)
            is ByteArray -> mmkv.putBytes(key, value)
            is Float -> mmkv.putFloat(key, value)
            is Int -> mmkv.putInt(key, value)
            is Long -> mmkv.putLong(key, value)
            is String -> mmkv.putString(key, value)
            else -> error("${value?.javaClass?.simpleName} Not Supported By MMKVUtils")
        }
    }

    fun getBoolean(key: String, defValue: Boolean = false) = mmkv.getBoolean(key, defValue)

    fun getBytes(key: String, defValue: ByteArray? = null) = mmkv.getBytes(key, defValue)

    fun getFloat(key: String, defValue: Float = 0f) = mmkv.getFloat(key, defValue)

    fun getInt(key: String, defValue: Int = 0) = mmkv.getInt(key, defValue)

    fun getLong(key: String, defValue: Long = 0L) = mmkv.getLong(key, defValue)

    fun getString(key: String, defValue: String? = null) = mmkv.getString(key, defValue)

}