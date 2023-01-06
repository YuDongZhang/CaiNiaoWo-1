package com.cainiaowo.service.assistant

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.utils.HOST_DEV
import com.cainiaowo.common.utils.HOST_RELEASE
import com.cainiaowo.common.utils.getBaseHost
import com.cainiaowo.common.utils.setBaseHost
import com.cainiaowo.service.R
import com.didichuxing.doraemonkit.kit.AbstractKit

/**
 * 用于配置切换不同的host,调试工具
 */
class ServerHostKit : AbstractKit() {

    override val icon = R.drawable.icon_server_host

    override val name = R.string.str_change_host

    private val hostMap = mapOf(
        "开发环境" to HOST_DEV,
        "正式环境" to HOST_RELEASE
    )

    private val names = hostMap.keys.toTypedArray()
    private val hosts = hostMap.values.toTypedArray()

    override fun onAppInit(context: Context?) {

    }

    override fun onClick(context: Context?) {
        val position = hosts.indexOf(getBaseHost()) % hosts.size
        context ?: return ToastUtils.showShort("Context对象为空")
        AlertDialog.Builder(context)
            .setTitle(name)
            .setSingleChoiceItems(names, position) { dialog, which ->
                setBaseHost(hosts[which])
                dialog.dismiss()
            }.show()
    }

}