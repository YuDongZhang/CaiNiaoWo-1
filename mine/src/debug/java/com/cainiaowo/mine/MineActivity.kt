package com.cainiaowo.mine

import android.graphics.Color
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.mine.databinding.ActivityMineBinding
import com.cainiaowo.mine.widget.ItemSettingsBean

/**
 * 调试用的 mine 模块入口Activity
 */
class MineActivity : BaseActivity<ActivityMineBinding>() {

    override fun getLayoutRes() = R.layout.activity_mine

    override fun initView() {
        super.initView()
        mBinding.apply {
            // 第一种：通过对自定义View直接进行操作的方式
//            val itemSettingsBean = ItemSettingsBean(title="修改的Title标题")
//            isvCard.setInfo(bean)
//            itemSettingsBean.title="Title标题~~~"
//
//            isvCard.setOnClickArrowListener {
//                ToastUtils.showShort("点击Arrow")
//            }
//            isvCard.setOnClickListener {
//                ToastUtils.showShort("点击整个Item")
//            }



            // 第二种：通过DataBinding在Activity中直接操作,这需要在布局<data></data>中添加对应变量
            val itemSettingsBean = ItemSettingsBean(iconRes = R.drawable.ic_shoping, title = "修改的Title标题")

            val obBean = ObservableField(itemSettingsBean)
            bean = obBean

            itemSettingsBean.title = "Title标题~~~"
            itemSettingsBean.titleColor = Color.RED

            itemSettingsBean.arrowColor = R.color.colorPrimary

            //  itemSettingsBean.iconRes = "网络图片地址"

            isvCard.setOnClickArrowListener {
                ToastUtils.showShort("点击Arrow")
            }
            isvCard.setOnClickListener {
                ToastUtils.showShort("点击整个Item")
            }

        }
    }

}