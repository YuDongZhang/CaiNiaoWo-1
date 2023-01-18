package com.cainiaowo.study.adapter

import com.cainiaowo.service.utils.setImageSrcCompat
import com.cainiaowo.study.R
import com.cainiaowo.study.network.BoughtRsp
import com.cainiaowo.study.network.StudiedRsp
import com.cainiaowo.video.entity.CourseInfo
import com.cainiaowo.video.ui.VideoActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.daimajia.numberprogressbar.NumberProgressBar

/**
 * 最近学习适配器
 */
class StudiedAdapterTest :
    BaseQuickAdapter<StudiedRsp.Data, BaseViewHolder>(R.layout.item_course_study) {
    override fun convert(holder: BaseViewHolder, item: StudiedRsp.Data) {
        setImageSrcCompat(holder.getView(R.id.iv_badge_item_study), item.getDetailImgUrl())
        holder.setText(R.id.tv_title_item_study, item.name)
        holder.getView<NumberProgressBar>(R.id.npb_progress_item_study).progress =
            (item.progress * 100).toInt()
        holder.itemView.setOnClickListener {
            VideoActivity.openVideoActivity(
                context,
                CourseInfo(
                    item.id,
                    item.getDetailImgUrl(),
                    item.name,
                    item.getLearnAndCommentCountStr(),
                    item.is_free,
                    item.now_price,
                    item.cost_price
                )
            )
        }
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
        holder.itemView.setOnClickListener {
            VideoActivity.openVideoActivity(
                context,
                CourseInfo(
                    item.course?.id ?: error("id不存在"),
                    item.course.getDetailImgUrl(),
                    item.course.name,
                    item.course.getLearnAndCommentCountStr(),
                    item.course.is_free,
                    item.course.now_price,
                    item.course.cost_price
                )
            )
        }
    }
}
