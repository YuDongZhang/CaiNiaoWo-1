package com.cainiaowo.video.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseInfo(
    val id: Int,
    val img_url: String?,
    val name: String?,
    val comment: String?,
    val is_free: Int,
    val now_price: Double,
    val cost_price: Double
) : Parcelable {
    fun isFreeStr(): String {
        return if (is_free == 1) "免费" else "￥${now_price}"
    }
}