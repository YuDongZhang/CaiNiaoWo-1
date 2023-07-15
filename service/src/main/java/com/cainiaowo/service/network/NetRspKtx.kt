package com.cainiaowo.service.network

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.support.CaiNiaoUtils
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * 基础的网络返回数据结构
 */
@Keep   // @Keep注解表示混淆时会保留类的信息
data class BaseCaiNiaoRsp(
    val code: Int,  // 响应码
    val data: String?,  // 响应数据内容,返回的数据都是经过加密的String
    val message: String?    // 响应数据的结果描述
) {
    companion object {
        const val SERVER_CODE_FAILED = 0    // 接口请求响应业务处理 失败
        const val SERVER_CODE_SUCCESS = 1   // 接口请求响应业务处理 成功
/*
后端返回的响应码说明
|状态码 |说明|
|-- |-- |
|1| 成功|
|0 |失败|
|101| appid为空或者app不存在|
|102|签名错误|
|103|签名失效（已经使用过一次）|
|104|请求已失效（时间戳过期）|
|105|缺少必传参数|
|106|参数格式有误或者未按规则提交|
|201|缺少token|
|202|token无效/错误|
|203|token已过期|
|401|没有权限调用|
|501|数据库连接出错|
|502|读写数据库异常|
 */
    }
}

/**
 * 这里表示网络请求成功并得到业务服务器的响应。至于业务成功失败,另一说
 * 将BaseCaiNiaoRsp的对象转化为需要的对象类型,也就是将body.string转为entity
 * @return 返回需要的类型对象,可能为null,如果json解析失败的话
 *
 * 这个地方做的就是解密 ,
 */
inline fun <reified T> BaseCaiNiaoRsp.toEntity(): T? {
    if (data == null) {
        LogUtils.e("Server Response Json Ok, But data=null, $code,$message")
        return null
    }
    // gson不允许我们将json对象采用String,所以单独处理
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return CaiNiaoUtils.decodeData(this.data) as T
    }
    return kotlin.runCatching {
        //具体在这里解析
        GsonUtils.fromJson(CaiNiaoUtils.decodeData(this.data), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}

/**
 * 接口请求成功,但是业务返回code不是1的情况,crossinline使用后不能跳出整个函数块
 */
@OptIn(ExperimentalContracts::class)
inline fun BaseCaiNiaoRsp.onBizError(
    crossinline block: (code: Int, message: String) -> Unit
): BaseCaiNiaoRsp {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (code != BaseCaiNiaoRsp.SERVER_CODE_SUCCESS) {
        block.invoke(code, message ?: "Error Message Null")
    }
    return this
}

/**
 * 接口请求成功,且业务成功code == 1的情况
 * crossinline 加上之后 在 方法中return 不能返出代码块之外
 * reified T 这个地方就是具体的类型 , 看调用的地方
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T> BaseCaiNiaoRsp.onBizOK(
    crossinline action: (code: Int, data: T?, message: String?) -> Unit
): BaseCaiNiaoRsp {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    //这个是业务的处理的成功 , 处理成功之后的data ,toEntity<T>()是什么?
    //com/cainiaowo/login/repo/LoginRepo.kt: onBizOK 传入泛型对应到这里 ,
    //进入到 toEntity 进行解密
    if (code == BaseCaiNiaoRsp.SERVER_CODE_SUCCESS) {
        action.invoke(code, this.toEntity<T>(), message)
    }
    return this
}