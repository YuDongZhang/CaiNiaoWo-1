package com.cainiaowo.mine.network

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * mine模块用户信息的返回数据
 */
@Keep
@Parcelize
data class UserInfoRsp(
    val company: String?, // 菜鸟窝
    val desc: String?, // 菜鸟窝创始人,首席体验官兼首席客服。
    val email: String?, // ivan@163.com
    val focus_it: String?,
    val follower_count: Int, // 260000
    val following_count: Int, // 1
    val id: Long, // 1
    val job: String?, // 创始人
    val logo_url: String?, // //img.cniao5.com/FmDPz-vX7e0HGJYdqGo4sWCXti_j?imageMogr2/thumbnail/300x301/crop/!273x273a0a0
    val mobi: String?, // 1xxxxxx
    val real_name: String?, // 艾文
    val username: String?, // Ivan.wong
    val work_years: String? // 5-10
) : Parcelable