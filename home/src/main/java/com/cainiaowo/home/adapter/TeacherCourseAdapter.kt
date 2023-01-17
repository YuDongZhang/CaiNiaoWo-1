package com.cainiaowo.home.adapter

import android.graphics.Paint
import com.cainiaowo.home.R
import com.cainiaowo.home.databinding.ItemTeacherCourseBinding
import com.cainiaowo.home.network.PopTeacherListRsp
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 人气讲师Adapter
 */
class TeacherCourseAdapter(teacherCourseList: MutableList<PopTeacherListRsp.PopTeacherItem>) :
    BaseQuickAdapter<PopTeacherListRsp.PopTeacherItem, BaseDataBindingHolder<ItemTeacherCourseBinding>>(
        R.layout.item_teacher_course,
        teacherCourseList
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemTeacherCourseBinding>,
        item: PopTeacherListRsp.PopTeacherItem
    ) {
        holder.dataBinding?.apply {
            teacherItem = item
            tvOldPriceItemCourse.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
            executePendingBindings()
        }
    }
}