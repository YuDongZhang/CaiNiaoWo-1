package com.cainiaowo.study.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.service.repo.CaiNiaoUserInfo
import com.cainiaowo.study.adapter.BoughtCourseAdapter
import com.cainiaowo.study.adapter.StudiedAdapter
import com.cainiaowo.study.network.BoughtRsp
import com.cainiaowo.study.network.StudiedRsp
import com.cainiaowo.study.network.StudyInfoRsp
import com.cainiaowo.study.repo.IStudyResource

/**
 * 学习中心逻辑的viewModel
 */
class StudyViewModel(private val resource: IStudyResource) : BaseViewModel() {

    val liveStudyInfo: LiveData<StudyInfoRsp> = resource.liveStudyInfo
    val liveStudyList: LiveData<StudiedRsp> = resource.liveStudyList
    val liveBoughtList: LiveData<BoughtRsp> = resource.liveBoughtList

    val obUserInfo = ObservableField<CaiNiaoUserInfo>()

    /**
     * RecyclerView适配器
     */
    val studiedAdapter = StudiedAdapter()
    val boughtCourseAdapter = BoughtCourseAdapter()

    fun getStudyInfo() = serveLaunch {
        resource.getStudyInfo()
    }

    fun getStudyList(page: Int = 1, size: Int = 10) = serveLaunch {
        resource.getStudyList(page, size)
    }

    fun getBoughtCourse(page: Int = 1, size: Int = 10) = serveLaunch {
        resource.getBoughtCourse(page, size)
    }

}