package com.cainiaowo.common.network.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class DataResult<out R> {
    /**
     * 成功状态的时候
     */
    data class Success<out T>(val data: T) : DataResult<T>()

    /**
     * 错误、失败状态的时候
     */
    data class Error(val exception: Exception) : DataResult<Nothing>()

    /**
     * 加载数据中
     */
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * 返回结果是Success且data非null,此data表示接口响应的数据,并不是业务中具体某个值的数据(不是code,data,message中的data)
 * 这也是一个扩展
 */
val DataResult<*>.succeed
    get() = this is DataResult.Success && data != null


/*
 对于 DataResult 扩展
  <R> DataResult<R> , 怎么理解 , DataResult<R> 接受的泛型 R , 前面要声明泛型 <R>
  val a = service.checkRegister(mobi).serveData() a 带的泛型就是 DataResult<BaseCniaoRsp>
  在这里写 的时候还是用 R 标记 , 别人也可以用

  action: R.() -> Unit 解析出 dsl的形式 , 解析对应的数据类型, 对于他的属性的操作

  @OptIn(ExperimentalContracts::class) 就是个提醒


 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onSuccess(
    action: R.() -> Unit
): DataResult<R> {

    // 实验特性,需要加注解,并且需要添加compiler argument '-Xopt-in=kotlin.RequiresOptIn'
    // 下面这个最多执行一次
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    //第 ② 步 succeed看上面的判断 , 拿到的就是结果data ,成功后 invoke()把对象 (this as DataResult.Success).data
    //执行到上面 action: R.() -> Unit 这里面去
    //成功的时候返回是  (this as DataResult.Success).data 也就是
    if (succeed) action.invoke((this as DataResult.Success).data)

    //第 ① 步   return this返回的是自身 , BaseCniaoRsp 需要解密 转化
    return this
}

/**
 * 这是网络请求出现错误的时候的回调(比如说网络异常等问题或者404等),不是业务错误
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onFailure(
    action: (exception: Throwable) -> Unit
): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    //是 erroer 就执行另外一个
    if (this is DataResult.Error) action.invoke(exception)
    return this
}