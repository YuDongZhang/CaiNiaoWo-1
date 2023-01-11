package com.cainiaowo.study.adapter

import com.cainiaowo.service.utils.setImageSrcCompat
import com.cainiaowo.study.R
import com.cainiaowo.study.network.BoughtRsp
import com.cainiaowo.study.network.StudiedRsp
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.daimajia.numberprogressbar.NumberProgressBar

/**
 * 最近学习适配器
 */
class StudiedAdapter :
    BaseQuickAdapter<StudiedRsp.CourseInfo, BaseViewHolder>(R.layout.item_course_study) {
    override fun convert(holder: BaseViewHolder, item: StudiedRsp.CourseInfo) {
        setImageSrcCompat(holder.getView(R.id.iv_badge_item_study), item.getDetailImgUrl())
        holder.setText(R.id.tv_title_item_study, item.name)
        holder.getView<NumberProgressBar>(R.id.npb_progress_item_study).progress =
            item.progress.toInt()
    }
}

/**
 * 我的课程适配器
 */
class BoughtCourseAdapter :
    BaseQuickAdapter<BoughtRsp.Data, BaseViewHolder>(R.layout.item_course_study) {
    override fun convert(holder: BaseViewHolder, item: BoughtRsp.Data) {
        setImageSrcCompat(holder.getView(R.id.iv_badge_item_study), item.course?.getDetailImgUrl())
        holder.setText(R.id.tv_title_item_study, item.course?.name)
        holder.getView<NumberProgressBar>(R.id.npb_progress_item_study).progress =
            (item.course?.progress ?: 0).toInt()
    }
}
