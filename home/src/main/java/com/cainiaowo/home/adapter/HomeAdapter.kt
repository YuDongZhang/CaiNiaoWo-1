package com.cainiaowo.home.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cainiaowo.home.R
import com.cainiaowo.home.databinding.ItemHomeBinding
import com.cainiaowo.home.network.JobCourseListRsp
import com.cainiaowo.home.network.NormalCourseListRsp
import com.cainiaowo.home.network.PopTeacherListRsp
import com.cainiaowo.home.utils.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 首页整体界面的Adapter
 */
class HomeAdapter(homeList: MutableList<HomeItem>) :
    BaseQuickAdapter<HomeItem, BaseDataBindingHolder<ItemHomeBinding>>(
        R.layout.item_home,
        homeList
    ) {

    override fun convert(holder: BaseDataBindingHolder<ItemHomeBinding>, item: HomeItem) {
        holder.dataBinding?.apply {
            title = item.title
            rvItemHome.adapter = when (item.type) {
                TYPE_JOB_COURSE -> {
                    rvItemHome.layoutManager = GridLayoutManager(context, 2)
                    JobCourseAdapter(item.data as JobCourseListRsp)
                }
                TYPE_NEW_COURSE,
                TYPE_LIMIT_FREE_COURSE,
                TYPE_ANDROID_COURSE,
                TYPE_AI_COURSE,
                TYPE_BD_COURSE,
                TYPE_SUGGEST_COURSE -> {
                    rvItemHome.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    NormalCourseAdapter(item.data as NormalCourseListRsp)
                }
                TYPE_TEACHER_COURSE -> {
                    rvItemHome.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    TeacherCourseAdapter(item.data as PopTeacherListRsp)
                }
                else -> error("Type Error:${item.type}")
            }
            executePendingBindings()
        }
    }

}

/**
 * 整个Home页面的Adapter单项
 * type,title与HomeFragment中的companion object对应
 * data对应具体的数据
 */
data class HomeItem(
    val type: Int,
    val title: String?,
    val data: Any?
)