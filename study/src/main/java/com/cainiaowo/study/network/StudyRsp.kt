package com.cainiaowo.study.network

import androidx.annotation.Keep
import com.cainiaowo.common.utils.getBaseHost


/**
 * Study模块网络请求的返回数据
 */

/**
 * 学习详情
 */
@Keep
data class StudyInfoRsp(
    val rank: Int, // 排行榜
    val today_study_time: Int, //今日时长 秒
    val total_study_time: Int //总时长
) {

    fun getRankStr(): String {
        return "第${rank}名"
    }

    fun getTodayStudyTimeStr(): String {
        return "${today_study_time / 60}分钟"
    }

    fun getTotalStudyTimeStr(): String {
        return "${total_study_time / 60}分钟"
    }
}

/**
 * 学习过的课程列表
 */
@Keep
data class StudiedRsp(
    val datas: List<CourseInfo>?,
    val page: Int,
    val size: Int,
    val total: Int,
    val total_page: Int
) {
    @Keep
    data class CourseInfo(
        val brief: String?,
        val comment_count: Int,
        val cost_price: Double,
        val course: Course?,
        val course_type: Int,
        val current_price: Double,
        val get_method: Int,
        val first_category: Category?,
        val id: Int,
        val left_expriy_days: Int,//剩余天数？
        var img_url: String?,//接口返回的，部分残缺了http://host,
        val is_distribution: Boolean,
        val is_free: Int,
        val is_live: Int,
        val is_pt: Boolean,
        val lessons_count: String?,
        val lessons_played_time: Int,
        val name: String?,
        val now_price: Double,
        val number: Int,
        val original_price: Double,
        val progress: Double,
        val title: String?
    ) {

        fun getDetailImgUrl(): String? {
            return img_url?.apply {
                // 有些图片返回缺少了https:,额外处理
                if (startsWith("/")) {
                    return "https:$this"
                }
            }
        }

        @Keep
        data class Course(
            val h5site: String?,
            val id: Int,
            val img_url: String?,
            val name: String?,
            val website: String?
        )

        @Keep
        data class Category(
            val code: String?,
            val id: Int,
            val title: String?
        )
    }
}

/**
 * 购买的课程
 */
@Keep
data class BoughtRsp(
    val datas: List<Data>?,
    val page: Int, // 1
    val size: Int, // 10
    val total: Int, // 11
    val total_page: Int // 2
) {
    @Keep
    data class Data(
        val cancel_time: String?, // 2020-11-24T14:34:48
        val course: CourseInfo?,
        val created_time: String?, // 2019-11-25 14:34:48
        val get_method: Int, // 1
        val id: Int, // 103865
        val is_expired: Boolean, // false
        val left_expiry_days: Int, // 113
        val order_time: String?, // 2019-11-25T14:34:13
        val product_id: Int, // 16
        val product_type: Int // 4
    ) {
        @Keep
        data class CourseInfo(
            val brief: String?, // null
            val comment_count: Int, // 0
            val cost_price: Double, // 10299
            val course: CourseDetail?,
            val first_category: Category?,
            val id: Int, // 16
            val img_url: String?, // //img.cniao5.com/o_1dd854ee6uqd1a9fc721bf110qs7.png
            val is_distribution: Boolean, // false
            val is_free: Int, // 0
            val is_live: Int, // 0
            val is_pt: Boolean, // false
            val lessons_played_time: Int, // 0
            val name: String?, // 预付99元锁定机器学习vip就业大课6999元的优惠名额
            val now_price: Double, // 1500
            val number: Int, // 2
            val progress: Double, // 0
            val title: String? // Android资深工程师（NDK/音视频/图像方向）
        ) {

            fun getDetailImgUrl(): String? {
                return img_url?.apply {
                    // 有些图片返回缺少了https:,额外处理
                    if (startsWith("/")) {
                        return "https:$this"
                    }
                }
            }

            @Keep
            data class CourseDetail(
                val h5site: String?, // https://h5.cniao5.com/skill/10
                val id: Int, // 10205
                val img_url: String?, // //img.cniao5.com/o_1du7o1k6ei45t1cm0h1skt1i4tm.png
                val name: String?, // Android 音视频开发工程师
                val website: String? // https://www.cniao5.com/skill/10
            )

            @Keep
            data class Category(
                val code: String?, // python
                val id: Int, // 7
                val title: String? // Python
            )
        }
    }
}