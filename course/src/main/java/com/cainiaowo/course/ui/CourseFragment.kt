package com.cainiaowo.course.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.course.R
import com.cainiaowo.course.databinding.FragmentCourseBinding
import com.cainiaowo.course.paging.CourseFooterAdapter
import com.cainiaowo.course.paging.CoursePagerAdapter

import com.cainiaowo.course.viewmodel.CourseViewModel
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupPosition
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 课程
 */
class CourseFragment : BaseFragment() {

    private val viewModel: CourseViewModel by viewModel()

    private lateinit var mBinding: FragmentCourseBinding

    //初始化为all,点击上方tabitem之后记得重新赋值
    private var code = "all"     //方向从课程分类接口获取    默认 all;例如 android,python
    private var difficulty = -1     //难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
    private var is_free = -1     //价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
    private var q = -1   //排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
    /*
    * 默认code = all
    * "code": "actual_combat","title": "实战"
      "code": "bd","title": "大数据"
      "code": "android","title": "Android"
      "code": "python",title": "Python"
      "code": "java"，"title": "Java"
    * */

    override fun getLayoutRes(): Int {
        return R.layout.fragment_course
    }

    private val adapter = CoursePagerAdapter()

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        mBinding = FragmentCourseBinding.bind(view)
        //also 下面可以拿到databing引用 , 从下面it 可以理解就是 databinding  , it一般就是指上面这一块
        return mBinding.apply {
            vm = viewModel
            rvCourse.adapter = adapter.withLoadStateFooter(CourseFooterAdapter {
                adapter.retry()
            })
        }
        addListener()
    }

    override fun initData() {
        super.initData()
        viewModel.getCourseCategory()
        //获取课程类型下的数据列表
        viewModel.apply {
            adapter.addLoadStateListener {
                when (it.refresh) {
                    is LoadState.NotLoading -> {

                    }
                    is LoadState.Loading -> {

                    }
                    is LoadState.Error -> {

                    }
                }
            }

            //课程分类
            liveCourseCategory.observerKt { types ->

                //清除所有的tabitem 因为返回的数据没有"全部"，要添加上，以防刷新丢掉了"全部"
                mBinding.apply {
                    tlCourseCategory.removeAllTabs()
                    val tab = tlCourseCategory.newTab().also { tab ->
                        tab.text = "全部"
                    }
                    tlCourseCategory.addTab(tab)
                    //对每个元素都执行指定的操作
                    types?.forEach { item ->
                        tlCourseCategory.addTab(
                            tlCourseCategory.newTab().also { tab ->
                                tab.text = item.title
                            }
                        )
                    }
                }
//
            }

            /*
               * tablayout点击事件
               * 打开的时候会点击一次
               * */
            //  tlCourseCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //tab被点击的时候回调 进入界面触发all
//                override fun onTabSelected(tab: TabLayout.Tab?) {
//                    //拿到获取的CourseType.value
//                    val courseTypes = liveCourseCategory.value
//                    //拿到点击的tab的position
//                    val index = tab?.position ?: 0
//                    code = if (index > 0) { //根据tab的选择改变code的值
//                        /*
//                        * 界面上第一个是全部,position = 0
//                        * 第二个是 xx,position = 1, 但是在数据源中是0
//                        * */
//                        courseTypes?.get(index - 1)?.code ?: "all"
//                    } else "all"
//                    lifecycleScope.launchWhenCreated {
//                        getCourseList(code = code).collectLatest {
//                            adapter.submitData(it)
//                        }
//                    }
//                }
//
//                //tab未被点击时回调
//                override fun onTabUnselected(tab: TabLayout.Tab?) {}
//
//                //tab重新点击时回调
//                override fun onTabReselected(tab: TabLayout.Tab?) {}
//            })

            // PagingAdapter监听Load状态
//            adapter.addLoadStateListener { loadState ->
//                when (loadState.refresh) {
//                    is LoadState.NotLoading -> {
//                        progressBar.visibility = View.GONE
//                        rvCourse.visibility = View.VISIBLE
//                    }
//                    is LoadState.Loading -> {
//                        progressBar.visibility = View.VISIBLE
//                        rvCourse.visibility = View.GONE
//                    }
//                    is LoadState.Error -> {
//                        val state = loadState.refresh as LoadState.Error
//                        mBinding.progressBar.visibility = View.GONE
//                        ToastUtils.showShort("Load Error: ${state.error.message}")
//                    }
//                }
//            }

            // 筛选类型、难度、价格、排序,使用XPopup
//            this.apply {
//                courseCategorySelector.setOnClickListener {
//                    courseCategoryPopup.show()
//                }
//                courseLevelSelector.setOnClickListener {
//                    courseLevelPopup.show()
//                }
//                coursePriceSelector.setOnClickListener {
//                    coursePricePopup.show()
//                }
//                courseSortSelector.setOnClickListener {
//                    courseSortPopup.show()
//                }
//            }
        }
    }

    private fun addListener() {
        // TabLayout选择Tab
        mBinding.tlCourseCategory.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val courseCategoryList = viewModel.liveCourseCategory.value ?: return
                val index = tab?.position ?: 0
                // 选中的tab对应去请求课程列表的code值,需要减去"全部"类别
                val code = if (index > 0) courseCategoryList[index - 1].code ?: "all" else "all"
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(code = code).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // PagingAdapter监听Load状态
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.rvCourse.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.rvCourse.visibility = View.GONE
                }
                is LoadState.Error -> {
                    val state = loadState.refresh as LoadState.Error
                    mBinding.progressBar.visibility = View.GONE
                    ToastUtils.showShort("Load Error: ${state.error.message}")
                }
            }
        }

        // 筛选类型、难度、价格、排序,使用XPopup
        mBinding.apply {
            courseCategorySelector.setOnClickListener {
                courseCategoryPopup.show()
            }
            courseLevelSelector.setOnClickListener {
                courseLevelPopup.show()
            }
            coursePriceSelector.setOnClickListener {
                coursePricePopup.show()
            }
            courseSortSelector.setOnClickListener {
                courseSortPopup.show()
            }
        }
    }


    private var item = -1
    private var lastCategoryPosition = 0
    private var lastLevelPosition = 0
    private var lastPricePosition = 0
    private var lastSortPosition = 0

    private val courseCategoryPopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.courseCategorySelector)
            .asAttachList(arrayOf("全部类型", "商业实战", "专项好课"), null) { position, text ->
                if (lastCategoryPosition == position) return@asAttachList
                lastCategoryPosition = position

                mBinding.courseCategorySelector.text = text
                item = when (position) {
                    0 -> -1
                    1 -> 3
                    2 -> 1
                    else -> -1
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(courseType = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

    private val courseLevelPopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.courseLevelSelector)
            .asAttachList(arrayOf("全部难度", "初级", "中级", "高级"), null) { position, text ->
                if (lastLevelPosition == position) return@asAttachList
                lastLevelPosition = position

                mBinding.courseLevelSelector.text = text
                item = when (position) {
                    0 -> -1
                    else -> position
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(difficulty = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

    private val coursePricePopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.coursePriceSelector)
            .asAttachList(arrayOf("全部价格", "免费", "付费"), null) { position, text ->
                if (lastPricePosition == position) return@asAttachList
                lastPricePosition = position

                mBinding.coursePriceSelector.text = text
                item = when (position) {
                    0 -> -1
                    1 -> 1
                    2 -> 0
                    else -> -1
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(isFree = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

    private val courseSortPopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.courseSortSelector)
            .asAttachList(arrayOf("默认排序", "评价最高", "学习最多"), null) { position, text ->
                if (lastSortPosition == position) return@asAttachList
                lastSortPosition = position

                mBinding.courseSortSelector.text = text
                item = when (position) {
                    0 -> -1
                    else -> position
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(q = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

}

