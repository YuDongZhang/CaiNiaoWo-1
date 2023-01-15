package com.cainiaowo.course.network

import androidx.annotation.Keep

/**
 * 所有分类
 */
@Keep
class CourseCategoryRsp : ArrayList<CourseCategoryItem>()

/**
 * 单个分类对应的内容
 * [code] 获取课程列表的分类课程时使用
 * [id] 类型id
 * [title] 课程名
 */
@Keep
data class CourseCategoryItem(
    val code: String?,
    val id: Int,
    val title: String?
)

/**
 * 课程列表
 */
@Keep
data class CourseListRsp(
    val datas: List<Course>?,
    val page: Int,
    val size: Int,
    val total: Int,
    val total_page: Int
) {
    @Keep
    data class Course(
        val brief: String?, // 课程简介
        val comment_count: Int, // 精选评论数量
        val cost_price: Double, // 原价
        val expiry_day: Int, // 课程学习有效期（天）
        val finished_lessons_count: Int, // 已更新的课时数
        val first_category: CourseCategoryItem?, // 一级分类
        val id: Int, // 课程id
        val img_url: String?, // 封面
        val is_free: Int, // 是否是免费课程
        val is_live: Int, // 是否是直播课程
        val is_pt: Boolean, // 是否参加了拼团活动
        val is_share_card: Boolean, // 是否加入了学习邀请卡活动
        val lessons_count: Int, // 总课时
        val lessons_played_time: Int, // 学习人数
        val name: String?, // 名字
        val now_price: Double // 当前价格
    ) {

        fun getDetailImgUrl(): String? {
            return img_url?.apply {
                // 有些图片返回缺少了https:,额外处理
                if (startsWith("/")) {
                    return "https:$this"
                }
            }
        }

        fun getLearnAndCommentCountStr(): String {
            return "$lessons_played_time     ${comment_count}人评价"
        }

        fun isFreeStr(): String {
            return if (is_free == 1) "免费" else "￥${now_price}"
        }
    }
}