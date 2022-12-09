package com.cainiaowo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cainiaowo.app.databinding.ActivityMainBinding
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.common.ktx.bindView
import com.cainiaowo.common.widget.BnvVp2Mediator
import com.cainiaowo.course.CourseFragment
import com.cainiaowo.home.HomeFragment
import com.cainiaowo.mine.MineFragment
import com.cainiaowo.study.StudyFragment

//AppCompatActivity(R.layout.activity_main) 也可以写这种
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

            BnvVp2Mediator(bnvMain, vp2Main).attach()
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