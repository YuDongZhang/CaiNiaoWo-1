package com.cainiaowo.home.adapter

import android.graphics.Paint
import com.cainiaowo.home.R
import com.cainiaowo.home.databinding.ItemNormalCourseBinding
import com.cainiaowo.home.network.HomeCourseItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 新上好课、限时免费、Android精选、AI精选、大数据精选、学员推荐通用部分Adapter
 */
class NormalCourseAdapter(normalCourseList: MutableList<HomeCourseItem>) :
    BaseQuickAdapter<HomeCourseItem, BaseDataBindingHolder<ItemNormalCourseBinding>>(
        R.layout.item_normal_course,
        normalCourseList
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemNormalCourseBinding>,
        item: HomeCourseItem
    ) {
        holder.dataBinding?.apply {
            courseItem = item
            tvOldPriceItemCourse.paint.flags += Paint.STRIKE_THRU_TEXT_FLAG
            executePendingBindings()
        }
    }
}