package com.cainiaowo.app

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cainiaowo.app.databinding.ActivityMainBinding
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.course.CourseFragment
import com.cainiaowo.home.HomeFragment
import com.cainiaowo.mine.MineFragment
import com.cainiaowo.study.StudyFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_COURSE to CourseFragment(),
        INDEX_STUDY to StudyFragment(),
        INDEX_MINE to MineFragment(),
    )

    override fun initView() {
        super.initView()
        mBinding.apply {
            // ViewPager2适配器
            vp2Main.adapter = MainViewPagerAdapter(this@MainActivity, fragments)
            // ViewPager2切换页面关联到BottomNavigationView
            vp2Main.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bnvMain.selectedItemId = bnvMain.menu.getItem(position).itemId
                }
            })

            // 存储menu item对应的index索引位置
            val map = mutableMapOf<MenuItem, Int>()

            bnvMain.menu.forEachIndexed { index, item ->
                map[item] = index
            }

            // BottomNavigationView点击时切换ViewPager2中的fragment
            bnvMain.setOnNavigationItemSelectedListener { item ->
                vp2Main.currentItem =
                    map[item] ?: error("bottomNavigationView的itemId${item.itemId}没有对应viewPager2的元素")
                true
            }
        }
    }

    override fun initConfig() {
        super.initConfig()
    }

    companion object {
        const val INDEX_HOME = 0    // HomeFragment对应的索引位置
        const val INDEX_COURSE = 1  // CourseFragment对应的索引位置
        const val INDEX_STUDY = 2   // StudyFragment对应的索引位置
        const val INDEX_MINE = 3    // MineFragment对应的索引位置
    }
}

/**
 * 首页的ViewPager2适配器
 */
class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: Map<Int, Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position] ?: error("请确保fragments数据源和viewPager2的index匹配设置")
    }

}