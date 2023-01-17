package com.cainiaowo.home.network

import androidx.annotation.Keep

// region Banner列表
class BannerListRsp : ArrayList<BannerItem>()

@Keep
data class BannerItem(
    val client_url: String?,
    val created_time: String?,
    val id: Int,
    val img_url: String?,   // 图片地址
    val name: String?,
    val order_num: Int,
    val page_show: Int,
    val redirect_url: String?,  // 跳转地址
    val state: Int,
    val type: String?
) {
    fun getDetailImgUrl(): String? {
        return img_url?.apply {
            // 有些图片返回缺少了https:,额外处理
            if (startsWith("/")) {
                return "https:$this"
            }
        }
    }
}
// endregion

// region 首页模块的title与id等
class PageModuleListRsp : ArrayList<PageModuleItem>()

@Keep
data class PageModuleItem(
    val created_time: String?,
    val data_url: String?,
    val id: Int,    // 模块id
    val is_show_more: Int,
    val layout: Int,
    val more_redirect_url: String?,
    val scroll: Int,
    val sub_title: String?,
    val title: String?,   // 模块标题
    val type: Int
)
// endregion

// region 模块具体内容
// 1.就业班
class JobCourseListRsp : ArrayList<JobCourseListRsp.JobCourseItem>() {
    @Keep
    data class JobCourseItem(
        val apply_deadline_time: String?,
        val course: Course?,
        val created_time: String?,
        val current_price: Double,
        val graduate_time: String?,
        val id: Int,
        val is_apply_stop: Int,
        val learning_mode: Int,
        val lessons_count: Int,
        val lessons_time: Int,
        val number: Int,
        val original_price: Double,
        val start_class_time: String?,
        val status: Int,
        val student_count: Int,
        val student_limit: Int,
        val study_expiry_day: Int,
        val teacher_ids: String?,
        val title: String?
    ) {
        @Keep
        data class Course(
            val h5site: String?,
            val id: Int,
            val img_url: String?,
            val name: String?,
            val website: String?
        ) {
            fun getDetailImgUrl(): String? {
                return img_url?.apply {
                    // 有些图片返回缺少了https:,额外处理
                    if (startsWith("/")) {
                        return "https:$this"
                    }
                }
            }
        }
    }
}

// 2.新上好课 3.限时免费 4.Android精选 5.AI精选 6.大数据精选 7.学员推荐
class NormalCourseListRsp : ArrayList<HomeCourseItem>()

@Keep
data class HomeCourseItem(
    val brief: String?,
    val comment_count: Int,
    val cost_price: Double,
    val expiry_day: Int,
    val finished_lessons_count: Int,
    val first_category: FirstCategory?,
    val id: Int,
    val img_url: String?,
    val is_free: Int,
    val is_live: Int,
    val is_pt: Boolean,
    val is_share_card: Boolean,
    val lessons_count: Int,
    val lessons_played_time: Int,
    val name: String?,
    val now_price: Double
) {
    @Keep
    data class FirstCategory(
        val code: String?,
        val id: Int,
        val title: String?
    )

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


// 8.人气讲师
class PopTeacherListRsp : ArrayList<PopTeacherListRsp.PopTeacherItem>() {
    @Keep
    data class PopTeacherItem(
        val brief: String?,
        val company: String?,
        val id: Int,
        val job_title: String?,
        val logo_url: String?,
        val teacher_course: List<TeacherCourse>?,
        val teacher_name: String?
    ) {
        @Keep
        data class TeacherCourse(
            val cost_price: Double,
            val id: Int,
            val img_url: String?,
            val lessons_played_time: Int,
            val comment_count: Int,
            val name: String?,
            val now_price: Double,
            val score: Int
        ) {
            fun getDetailImgUrl(): String? {
                return img_url?.apply {
                    // 有些图片返回缺少了https:,额外处理
                    if (startsWith("/")) {
                        return "https:$this"
                    }
                }
            }
        }

        fun getDetailImgUrl(): String? {
            return logo_url?.apply {
                // 有些图片返回缺少了https:,额外处理
                if (startsWith("/")) {
                    return "https:$this"
                }
            }
        }

        fun courseImgUrl(): String {
            return if (teacher_course.isNullOrEmpty()) "" else teacher_course[0].getDetailImgUrl()
                ?: ""
        }

        fun courseTitle(): String {
            return if (teacher_course.isNullOrEmpty()) "" else teacher_course[0].name ?: ""
        }

        fun courseComment(): String {
            return if (teacher_course.isNullOrEmpty()) "" else "${teacher_course[0].lessons_played_time} " +
                    "${teacher_course[0].comment_count}人评价"
        }

        fun coursePrice(): String {
            return if (teacher_course.isNullOrEmpty()) "" else "${teacher_course[0].now_price}"
        }
    }
}
// endregion
