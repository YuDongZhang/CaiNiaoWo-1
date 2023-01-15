package com.cainiaowo.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cainiaowo.app.databinding.ActivityMainBinding
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.common.widget.BnvVp2Mediator
import com.cainiaowo.course.ui.CourseFragment
import com.cainiaowo.home.HomeFragment
import com.cainiaowo.mine.MineContainerFragment
import com.cainiaowo.study.ui.StudyFragment

//AppCompatActivity(R.layout.activity_main) 也可以写这种
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    /**
     * 使用这种方式可以复用Fragment
     * 如果不想复用Fragment,泛型可以设置成<Int, ()->Fragment>,然后在value部分使用{XxxFragment()},
     * 在MainViewPagerAdapter的createFragment中通过fragments[position]?.invoke()获取fragment实例
     */

    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_COURSE to CourseFragment(),
        INDEX_STUDY to StudyFragment(),
        INDEX_MINE to MineContainerFragment(),
    )

    override fun initView() {
        super.initView()
        mBinding.apply {
            // ViewPager2适配器
            vp2Main.adapter = MainViewPagerAdapter(this@MainActivity, fragments)

            BnvVp2Mediator(bnvMain, vp2Main) { _, viewPager2 ->
                // ViewPager2不滑动
                viewPager2.isUserInputEnabled = false
            }.attach()
        }
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

    //invoke获取实例
//    override fun createFragment(position: Int) =
//        fragments[position]?.invoke() ?: throw java.lang.IndexOutOfBoundsException("viewpage参数越界了")

}

//类型别名的函数定义
//typealias ResFragment = () -> Fragment