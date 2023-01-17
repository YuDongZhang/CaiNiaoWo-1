package com.cainiaowo.course.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cainiaowo.course.databinding.ItemCourseFooterBinding

class CourseFooterAdapter(
    private var errorBlock: (() -> Unit)? = null
) : LoadStateAdapter<CourseFooterAdapter.CourseFooterViewHolder>() {
    override fun onBindViewHolder(holder: CourseFooterViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {
                holder.apply {
                    binding.progressBar.visibility = View.GONE
                    binding.textView.text = "加载失败,点击重试..."
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.setOnClickListener {
                        errorBlock?.invoke()
                    }
                }
            }
            is LoadState.Loading -> {
                holder.apply {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textView.text = "加载中..."
                    binding.textView.visibility = View.VISIBLE
                }
            }
            is LoadState.NotLoading -> {
                holder.apply {
                    binding.progressBar.visibility = View.GONE
                    binding.textView.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = CourseFooterViewHolder.createViewHolder(parent)

    class CourseFooterViewHolder(val binding: ItemCourseFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun createViewHolder(parent: ViewGroup): CourseFooterViewHolder {
                return CourseFooterViewHolder(
                    ItemCourseFooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}