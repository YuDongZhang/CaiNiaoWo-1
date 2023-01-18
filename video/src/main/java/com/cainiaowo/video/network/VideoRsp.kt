package com.cainiaowo.video.network

import androidx.annotation.Keep

/**
 * 课程学习权限
 */
@Keep
data class CoursePermissionRsp(
    val cancel_time: String?,
    val days: Int,
    val get_method: Int,
    val is_true: Int, // 1表示有,0表示没有
    val type: String?
) {
    companion object {
        const val HAS_COURSE_PERMISSION = 1
        const val NO_COURSE_PERMISSION = 0
    }
}

/**
 * 一门课程分多个章节
 */
class CourseLessonsRsp : ArrayList<CourseLessonItem>()

/**
 * 一门课程的单个章节
 */
@Keep
data class CourseLessonItem(
    val begin_time: String?,
    val brief: String?,
    val bsort: Int, // 章节排序
    val cover_url: String?,
    val created_time: String?,
    val end_time: String?,
    val id: Int,    // 章节ID
    val is_open: Int, // 是否已经解锁
    val lessons: List<Lesson>?, // 章节下的课时
    val rank: Int,
    val teacher: String?,
    val title: String?  // 章节名称
) {
    /**
     * 章节下的课时
     */
    @Keep
    data class Lesson(
        val bsort: Int, // 课时排序
        val id: Int,
        val is_finished: Boolean, // 是否学完了该节课,用户登录的情况下才会有该字段
        val is_free: Int,  // 单节课是否是免费的
        val is_live: Int,
        val key: String?, // 课时的Key
        val live_plan_begin_time: String?,
        val live_plan_end_time: String?,
        val live_status: Int,
        val name: String?, // 课时的名称
        val state: Int, // 课时状态
        val video_info: VideoInfo?
    ) {
        /**
         * 关联的视频信息
         */
        @Keep
        data class VideoInfo(
            val duration: Int,
            val id: Int
        )
    }
}

/**
 * 课时播放地址
 */
@Keep
data class LessonUrlsRsp(
    val is_live: Int,
    val seek: Int, // 播放的起始位置
    val urls: Urls? // 播放地址
) {
    @Keep
    data class Urls(
        val flv: Flv?,
        val hls: Hls?,
        val orig: String?,  // 原始地址
        val rtmp: Rtmp?
    ) {
        fun getDetailOrig(): String? {
            return orig?.apply {
                if (startsWith("/")) {
                    return "https:$this"
                }
            }
        }

        @Keep
        data class Flv(
            val hd: String?, // //liveplay.cniao5.com/live/16_900.flv
            val sd: String?, // //liveplay.cniao5.com/live/16_550.flv
            val shd: String? // //liveplay.cniao5.com/live/16.flv
        ) {
            fun getDetailHd(): String? {
                return hd?.apply {
                    if (startsWith("/")) {
                        return "https:$this"
                    }
                }
            }
        }

        @Keep
        data class Hls(
            val hd: String?, // //liveplay.cniao5.com/live/16_900.m3u8
            val sd: String?, // //liveplay.cniao5.com/live/16_550.m3u8
            val shd: String? // //liveplay.cniao5.com/live/16.m3u8
        ) {
            fun getDetailHd(): String? {
                return hd?.apply {
                    if (startsWith("/")) {
                        return "https:$this"
                    }
                }
            }
        }

        @Keep
        data class Rtmp(
            val hd: String?, // rtmp://liveplay.cniao5.com/live/16_900
            val sd: String?, // rtmp://liveplay.cniao5.com/live/16_550
            val shd: String? // rtmp://liveplay.cniao5.com/live/16
        )
    }
}
