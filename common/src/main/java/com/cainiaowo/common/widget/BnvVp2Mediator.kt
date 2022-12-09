package com.cainiaowo.common.widget

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * BottomNavigationView和ViewPager2联动的中介者,可复用
 */
class BnvVp2Mediator(
    private val bnv: BottomNavigationView,
    private val vp2: ViewPager2,
    private val config: ((BottomNavigationView, ViewPager2) -> Unit)? = null
) {

    /**
     * 存储BottomNavigationView的menu的item和它对应的index索引位置
     */
    private val map = mutableMapOf<MenuItem, Int>()

    init {
        // item与其index存储
        bnv.menu.forEachIndexed { index, item ->
            map[item] = index
        }
    }

    fun attach() {
        config?.invoke(bnv, vp2)
        // ViewPager2切换页面关联到BottomNavigationView
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu.getItem(position).itemId
            }
        })

        // BottomNavigationView点击时切换ViewPager2中的fragment
        bnv.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem =
                map[item] ?: error("bottomNavigationView的itemId${item.itemId}没有对应viewPager2的元素")
            true
        }
    }
}