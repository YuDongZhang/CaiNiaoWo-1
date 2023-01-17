package com.cainiaowo.home.utils

import com.cainiaowo.home.network.HomeCourseItem


/*
 */
object HomeUtils {

    @JvmStatic
    fun parseStudentComment(info: HomeCourseItem?): String {
        return "${info?.lessons_count} ${info?.comment_count}人评价"
    }

    /*
    * 是否免费 免费返回true 否则返回false
    * */
    @JvmStatic
    fun parseFree(info: HomeCourseItem?): Boolean {
        return info?.is_free == 1
    }

}