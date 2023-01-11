package com.cainiaowo.study.repo

import androidx.lifecycle.LiveData
import com.cainiaowo.study.network.BoughtRsp
import com.cainiaowo.study.network.StudiedRsp
import com.cainiaowo.study.network.StudyInfoRsp

/**
 * 学习中心相关的抽象数据接口
 */
interface IStudyResource {

    val liveStudyInfo: LiveData<StudyInfoRsp>

    val liveStudyList: LiveData<StudiedRsp>

    val liveBoughtList: LiveData<BoughtRsp>

    /**
     * 学习情况
     */
    suspend fun getStudyInfo()

    /**
     * 最近学习
     */
    suspend fun getStudyList(page: Int, size: Int)

    /**
     * 我的课程
     */
    suspend fun getBoughtCourse(page: Int, size: Int)

}