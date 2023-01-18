package com.cainiaowo.video.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.cainiaowo.video.databinding.ItemCourseTitleBinding
import com.cainiaowo.video.databinding.ItemLessonBinding

class VideoLessonAdapter(private val callback: (Item) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == Item.TYPE_TITLE)
            CourseTitleViewHolder.createViewHolder(parent)
        else
            LessonViewHolder.createViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is CourseTitleViewHolder -> {
                holder.bind(item.title ?: "Title Null")
            }
            is LessonViewHolder -> {
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    callback.invoke(item)
                }
            }
            else -> error("不支持的ViewHolder类型")
        }
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int) = itemList[position].viewType

    fun setList(list: MutableList<Item>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    class CourseTitleViewHolder(private val binding: ItemCourseTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun createViewHolder(parent: ViewGroup): CourseTitleViewHolder {
                return CourseTitleViewHolder(
                    ItemCourseTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(title: String) {
            binding.title = title
            binding.executePendingBindings()
        }
    }

    class LessonViewHolder(private val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun createViewHolder(parent: ViewGroup): LessonViewHolder {
                return LessonViewHolder(
                    ItemLessonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}


/**
 * 单个Item可以分为  标题类型  和  具体的单节课  类型
 */
data class Item(
    val viewType: Int,  // 用于区分Item类型,TYPE_TITLE和TYPE_LESSON
    val title: String?, // TYPE_TITLE时表示章节名称;TYPE_LESSON表示单节课的名称
    val key: String? = null,   // 单节课才会有的key,用于获取播放地址
    val tryPlay: Boolean = false    // 单节课是否能够试看
) {
    var isPlaying = ObservableBoolean(false)    // 当前的选中状态,用于更改UI

    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_LESSON = 1
    }
}