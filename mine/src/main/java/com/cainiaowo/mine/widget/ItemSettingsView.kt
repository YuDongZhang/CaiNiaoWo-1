package com.cainiaowo.mine.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import com.cainiaowo.mine.R
import com.cainiaowo.mine.databinding.VItemSettingsBinding

/**
 * 自定义的设置item的组合控件
 */
class ItemSettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        // 第一步：关联布局v_layout_settings.xml
        VItemSettingsBinding.inflate(LayoutInflater.from(context),this,true)
    }

}

@Keep
data class ItemSettingsBean(
    var iconRes: Any = R.drawable.ic_gift_card,
    var title: String = "Title标题",
    var desc: String = "标题内容描述",
    var titleColor: Int = R.color.colorPrimaryText,
    var descColor: Int = R.color.colorSecondaryText,
    var iconColor: Int = 0,      // svg图片支持通过setTint的方式,png等不支持
    var arrowColor: Int = 0,
    var arrowRes: Any = R.drawable.ic_right
)