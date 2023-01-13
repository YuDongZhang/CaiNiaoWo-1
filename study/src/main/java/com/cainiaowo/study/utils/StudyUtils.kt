package com.cniao5.study.utils

import androidx.databinding.BindingAdapter
import com.cainiaowo.study.network.StudiedRsp

import com.daimajia.numberprogressbar.NumberProgressBar

/**
 * DataBinding数据解析
 */
object StudyUtils {
    @JvmStatic
    fun rankStr(rank: Int): String {
        return if (rank > 0) "第${rank}名" else "千里之外"
    }


    @JvmStatic
    fun parseStudentComment(info: StudiedRsp.Data?): String {
        return "${info?.lessons_played_time} ${info?.comment_count}人评价"
    }

    @JvmStatic
    fun parseFree(info: StudiedRsp.Data?): String {
        return if (info?.is_free == 1) "免费" else "￥${info?.now_price}"
    }

    /*
    * 有两种格式的返回值，判断该用哪种
    * */
    @JvmStatic
    fun parseTitle(info: StudiedRsp.Data?): String {
        return if (info?.course != null) {
            "${info.course.name}"
        } else {
            "${info?.name}"
        }
    }

    /*
    * 有两种格式的返回值，判断该用哪种
    * */
    @JvmStatic
    fun parseImage(info: StudiedRsp.Data?): String {
        return if (info?.course != null) {
            "${info.course.img_url}"
        } else {
            "${info?.img_url}"
        }
    }

    /*
    * 返回0-100之间随机的一个整数
    * */
    @JvmStatic
    fun getRandomProgress(): Double {
        return (0..100).random().toDouble()/100
    }

}

/*
* //NumberProgressBar扩展函数 将传来的progress*100转化为百分比形式 并且支持databinding加载
*
@BindingAdapter("app:progress_current", requireAll = false)
fun setProgress(pb: NumberProgressBar, progress: Double?) {
    pb.progress = ((progress ?: 0.0) * 100).toInt() //*100，转化为百分比显示
}


 */
 */