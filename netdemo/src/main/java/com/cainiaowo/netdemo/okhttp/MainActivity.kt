package com.cainiaowo.netdemo.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.netdemo.R
import com.cainiaowo.netdemo.okhttp.model.NetResponse
import com.cainiaowo.netdemo.okhttp.support.CaiNiaoUtils
import com.cainiaowo.netdemo.okhttp.support.IHttpCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getResult: TextView = findViewById(R.id.tv_get_result)
        val postResult: TextView = findViewById(R.id.tv_post_result)

        val httpApi: IHttpApi = OkHttpApi.getInstance()
        val loginBody = LoginReq()

        // get请求
        httpApi.get(
            emptyMap(),
            "https://course.api.cniao5.com/member/userinfo",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    LogUtils.d("success result : ${data.toString()}")
                    runOnUiThread {
                        getResult.text = data.toString()
                    }
                }

                override fun onFailed(error: Any?) {
                    LogUtils.d("failed msg : ${error.toString()}")
                }
            })

        // post请求
        httpApi.post(
            loginBody,
            "https://course.api.cniao5.com/accounts/course/10301/login",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    LogUtils.d("success result : ${data.toString()}")
                    runOnUiThread {
                        val result = data.toString()
                        val (_, dataObj, _) = GsonUtils.fromJson<NetResponse>(
                            result,
                            NetResponse::class.java
                        )
                        postResult.text = CaiNiaoUtils.decodeData(dataObj?.toString())
                    }
                }

                override fun onFailed(error: Any?) {
                    LogUtils.d("failed msg : ${error.toString()}")
                }
            })


    }

    data class LoginReq(val mobi: String = "13067732886", val password: String = "123456789")
}