package com.cainiaowo.service.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * 项目适配用的BindingAdapter
 */

/**
 * ImageView支持图片加载、绑定
 */
@BindingAdapter("app:srcCompat", requireAll = false)
fun setImageSrcCompat(iv: ImageView, src: Any?) {
    Glide.with(iv).load(src).into(iv)
}

// region ImageView app:tint    ImageViewBindingAdapter中未实现,所以如果在DataBinding布局中需要使用时自己实现
@BindingAdapter("app:tint")
fun setImageTint(iv: ImageView, @ColorRes color: Int) {
    if (color != 0) iv.setColorFilter(iv.resources.getColor(color))
}

@BindingAdapter("app:tint")
fun setImageTint2(iv: ImageView, @ColorInt color: Int) {
    // 比如 Color.RED
    if (color != 0) iv.setColorFilter(color)
}
// endregion

// region TextView android:textColor  TextViewBindingAdapter中未实现,所以如果在DataBinding布局中需要使用时自己实现
@BindingAdapter("android:textColor")
fun setTextColor(tv: TextView, @ColorRes color: Int) {
    if (color != 0) tv.setTextColor(tv.resources.getColor(color))
}

@BindingAdapter("android:textColor")
fun setTextColor2(tv: TextView, @ColorInt color: Int) {
    if (color != 0) tv.setTextColor(color)
}
// endregion