package com.cainiaowo.course.paging

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cainiaowo.course.databinding.ItemCourseBinding
import com.cainiaowo.course.network.CourseListRsp

/**
 * RecyclerView适配器,需要继承PagingDataAdapter
 */
class CoursePagerAdapter : PagingDataAdapter<CourseListRsp.Course, CourseViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CourseViewHolder.createViewHolder(parent)

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<CourseListRsp.Course>() {
                override fun areItemsTheSame(
                    oldItem: CourseListRsp.Course,
                    newItem: CourseListRsp.Course
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: CourseListRsp.Course,
                    newItem: CourseListRsp.Course
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}

class CourseViewHolder(private val binding: ItemCourseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun createViewHolder(parent: ViewGroup): CourseViewHolder {
            return CourseViewHolder(
                ItemCourseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(course: CourseListRsp.Course) {
        binding.course = course
        binding.tvOldPriceItemCourse.paint.flags += Paint.STRIKE_THRU_TEXT_FLAG // 添加删除线
        binding.executePendingBindings()
    }

}