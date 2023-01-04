package com.cainiaowo.service.repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 数据库操作Helper类
 */
object CaiNiaoDbHelper {

    /**
     * 获取room数据表中存储的userInfo,return LiveData形式
     */
    fun getLiveUserInfo(context: Context) =
        CaiNiaoDatabase.getInstance(context).userDao.queryLiveUser()

    /**
     * 以普通数据对象的形式获取userInfo
     */
    fun getUserInfo(context: Context) =
        CaiNiaoDatabase.getInstance(context).userDao.queryUser()

    /**
     * 删除数据表中的userInfo信息,登出时使用
     */
    fun deleteUserInfo(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            getUserInfo(context)?.let { userInfo ->
                CaiNiaoDatabase.getInstance(context).userDao.deleteUser(userInfo)
            }
        }
    }

    /**
     * 新增用户数据到数据表,登录时使用
     */
    fun insertUserInfo(context: Context, user: CaiNiaoUserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            CaiNiaoDatabase.getInstance(context).userDao.insertUser(user)
        }
    }
}