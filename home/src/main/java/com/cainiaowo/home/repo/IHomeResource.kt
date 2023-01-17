package com.cainiaowo.home.repo

import androidx.lifecycle.LiveData
import com.cainiaowo.common.network.model.DataResult
import com.cainiaowo.home.network.BannerListRsp
import com.cainiaowo.home.network.PageModuleListRsp
import com.cainiaowo.service.network.BaseCaiNiaoRsp

interface IHomeResource {

    val liveBannerList: LiveData<BannerListRsp>

    val livePageModuleList: LiveData<PageModuleListRsp>

    suspend fun getBannerList()

    suspend fun getPageModuleList()

    suspend fun getPageModuleItems(moduleId: Int): DataResult<BaseCaiNiaoRsp>
}