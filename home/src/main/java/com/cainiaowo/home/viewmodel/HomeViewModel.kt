package com.cainiaowo.home.viewmodel

import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.home.repo.IHomeResource

class HomeViewModel(private val resource: IHomeResource) : BaseViewModel() {

    val liveBannerList = resource.liveBannerList

    val livePageModuleList = resource.livePageModuleList

    fun getBannerList() = serveAwait {
        resource.getBannerList()
    }

    fun getPageModuleList() = serveAwait {
        resource.getPageModuleList()
    }

    suspend fun getPageModuleItems(moduleId: Int) =
        resource.getPageModuleItems(moduleId)
}