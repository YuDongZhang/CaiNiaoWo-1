package com.cainiaowo.mine.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
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

    private var itemBean = ItemSettingsBean()
    private val obItemInfo = ObservableField(itemBean)

    init {
        // 第一步：关联布局v_layout_settings.xml
        VItemSettingsBinding.inflate(LayoutInflater.from(context), this, true).apply {
            // 第二步：将ObservableField<ItemSettingsBean>赋值给DataBinding中的info
            info = obItemInfo
        }
        // 设置背景颜色,为了观察修改颜色后的效果
        setBackgroundColor(Color.WHITE)

        // 第三步：读取自定义属性配置
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSettingsView).apply {
            // icon设置
            itemBean.iconRes =
                getResourceId(R.styleable.ItemSettingsView_icon, R.drawable.ic_gift_card)
            val iconRGB = getColor(R.styleable.ItemSettingsView_iconColor, 0)
            itemBean.iconColor = iconRGB
            // title设置
            itemBean.title = getString(R.styleable.ItemSettingsView_title) ?: "Title标题"
            val titleRGB = getColor(
                R.styleable.ItemSettingsView_titleColor,
                resources.getColor(R.color.colorPrimaryText)
            )
            itemBean.titleColor = titleRGB
            // desc设置
            itemBean.desc = getString(R.styleable.ItemSettingsView_desc) ?: "标题内容描述"
            val descRGB = getColor(R.styleable.ItemSettingsView_descColor, 0)
            itemBean.descColor = descRGB
            // arrow设置
            itemBean.arrowRes =
                getResourceId(R.styleable.ItemSettingsView_arrow, R.drawable.ic_right)
            val arrowRGB = getColor(
                R.styleable.ItemSettingsView_arrowColor,
                resources.getColor(R.color.colorSecondaryText)
            )
            itemBean.arrowColor = arrowRGB
        }

        // 回收 recycle
        attributes.recycle()
        // endregion
    }

    // region 设置资源  setTitle、setDesc、setIcon和setArrow对应自定义属性,这样就不用写BindingAdapter

    fun setInfo(info: ItemSettingsBean) {
        itemBean = info
        obItemInfo.set(info)
    }

    fun setTitle(title: String) {
        itemBean.title = title
    }

    fun setDesc(desc: String) {
        itemBean.desc = desc
    }

    fun setIcon(iconRes: Any) {
        itemBean.iconRes = iconRes
    }

    fun setArrow(arrowRes: Any) {
        itemBean.arrowRes = arrowRes
    }

    // endregion

    //region 点击事件

    fun setOnClickIconListener(listener: OnClickListener) {
        itemBean.iconListener = listener
    }

    fun setOnClickTitleListener(listener: OnClickListener) {
        itemBean.titleListener = listener
    }

    fun setOnClickDescListener(listener: OnClickListener) {
        itemBean.descListener = listener
    }

    fun setOnClickArrowListener(listener: OnClickListener) {
        itemBean.arrowListener = listener
    }

    //endregion

    //region 设置颜色   setIconColor、setTitleColor、setDescColor和setArrowColor对应自定义属性,这样就不用写BindingAdapter

    fun setIconColor(colorRes: Int) {
        itemBean.iconColor = colorRes
    }

    fun setTitleColor(colorRes: Int) {
        itemBean.titleColor = colorRes
    }

    fun setDescColor(colorRes: Int) {
        itemBean.descColor = colorRes
    }

    fun setArrowColor(colorRes: Int) {
        itemBean.arrowColor = colorRes
    }

    //endregion

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // 只要设置了这个控件即ItemSettingsView.OnClickListener后,其它子控件的事件都不响应
        return hasOnClickListeners()
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
) {
    var iconListener: View.OnClickListener? = null
    var titleListener: View.OnClickListener? = null
    var descListener: View.OnClickListener? = null
    var arrowListener: View.OnClickListener? = null
}