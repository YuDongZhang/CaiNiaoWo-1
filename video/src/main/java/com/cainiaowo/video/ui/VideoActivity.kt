package com.cainiaowo.video.ui

import android.content.Context
import android.content.Intent
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.video.R
import com.cainiaowo.video.adapter.Item
import com.cainiaowo.video.adapter.VideoLessonAdapter
import com.cainiaowo.video.databinding.ActivityVideoBinding
import com.cainiaowo.video.entity.CourseInfo
import com.cainiaowo.video.network.CoursePermissionRsp
import com.cainiaowo.video.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoActivity : BaseActivity<ActivityVideoBinding>() {

    private val viewModel: VideoViewModel by viewModel()

    override fun getLayoutRes() = R.layout.activity_video

    /**
     * 上次点击的key
     */
    private var lastKey: String? = ""

    /**
     * 当前播放视频的title
     */
    private var title: String? = ""

    private val items = mutableListOf<Item>()
    private val videoLessonAdapter = VideoLessonAdapter { clickedItem ->
        if (lastKey == clickedItem.key) return@VideoLessonAdapter
        lastKey = clickedItem.key
        title = clickedItem.title
        // 遍历,刷新UI选中效果
        items.forEach { item ->
            item.isPlaying.set(item.key == clickedItem.key)
        }
        // 获取点击Lesson的Url
        clickedItem.key?.let { key ->
            viewModel.getLessonUrls(key)
        }
    }

    override fun initView() {
        super.initView()
        val courseInfo = intent.getParcelableExtra<CourseInfo>(COURSE_INFO)
            ?: return ToastUtils.showShort("CourseInfo传递失败")

        mBinding.apply {
            course = courseInfo
            adapter = videoLessonAdapter
            jzVideo.reset()
        }

        viewModel.apply {
            // 获取权限
            getCoursePermission(courseInfo.id)

            // 权限观察
            liveCoursePermission.observerKt { coursePermission ->
                // 获取课程目录
                getCourseLessons(courseInfo.id)
                if (coursePermission?.is_true == CoursePermissionRsp.NO_COURSE_PERMISSION) {
                    ToastUtils.showShort("没有课程权限")
                }
            }
            // 课程目录观察
            liveCourseLessons.observerKt { chapters ->
                items.clear()
                // 遍历所有章
                chapters?.forEach { chapter ->
                    items.add(
                        Item(
                            Item.TYPE_TITLE,
                            "第${chapter.bsort}章 ${chapter.title}"
                        )
                    )
                    // 遍历一章下的所有节
                    chapter.lessons?.forEach { lesson ->
                        items.add(
                            Item(
                                Item.TYPE_LESSON,
                                "${chapter.bsort}-${lesson.bsort} ${lesson.name}",
                                lesson.key,
                                lesson.is_free == 1
                            )
                        )
                    }
                }
                videoLessonAdapter.setList(items)
            }
            // 获取url播放视频
            liveLessonUrls.observerKt { lesson ->
                // 获取url
                val flvHD = lesson?.urls?.flv?.getDetailHd()
                val hlsHD = lesson?.urls?.hls?.getDetailHd()
                val rtmp = lesson?.urls?.rtmp?.hd
                val url: String? = if (flvHD.isNullOrBlank()) {
                    if (hlsHD.isNullOrBlank()) {
                        if (rtmp.isNullOrBlank()) {
                            lesson?.urls?.getDetailOrig()
                        } else {
                            rtmp
                        }
                    } else {
                        hlsHD
                    }
                } else {
                    flvHD
                }
                mBinding.jzVideo.setUp(
                    url,
                    title
                )
                mBinding.jzVideo.startVideo()
            }
        }
    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    companion object {
        private const val COURSE_INFO = "course_info"

        fun openVideoActivity(context: Context, courseInfo: CourseInfo) {
            context.startActivity(Intent(context, VideoActivity::class.java).apply {
                putExtra(COURSE_INFO, courseInfo)
            })
        }
    }
}