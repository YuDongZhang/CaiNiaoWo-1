package com.cainiaowo.service.repo

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * App的公共业务基础数据类
 */

// 3.database
@Database(entities = [CaiNiaoUserInfo::class], version = 1, exportSchema = false)
abstract class CaiNiaoDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        private const val CAINIAO_DB_NAME = "cainiao_db"

        @Volatile
        private var instance: CaiNiaoDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CaiNiaoDatabase {
            return instance ?: Room.databaseBuilder(
                context,
                CaiNiaoDatabase::class.java,
                CAINIAO_DB_NAME
            ).build().also { instance = it }
        }
    }
}


// 1.entity的定义
@Entity(tableName = "table_cainiao_user")
data class CaiNiaoUserInfo(
    @PrimaryKey
    val id: Int,                         // 主键
    val course_permission: Boolean,     // 是否拥有该课程的学习权限
    val token: String?,                 // 登录凭证，与用户相关的接口都需要传该token值。（PS:没有课程学习权限的用户不返回该字段）
    @Embedded
    val user: User?                     // 用户信息
) {
    @Keep
    data class User(
        @ColumnInfo(name = "cainiao_user_id")
        val id: Int,                  // 用户id
        val is_bind_phone: Boolean,  // 是否已经绑定手机
        val logo_url: String?,      // 用户头像
        val username: String?       // 用户名
    )
}

// 2.DAO层的定义
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: CaiNiaoUserInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: CaiNiaoUserInfo)

    @Delete
    fun deleteUser(user: CaiNiaoUserInfo)

    @Query("select * from table_cainiao_user where id=:id")
    fun queryUser(id: Int = 0): CaiNiaoUserInfo?

    @Query("select * from table_cainiao_user where id=:id")
    fun queryLiveUser(id: Int = 0): LiveData<CaiNiaoUserInfo>
}