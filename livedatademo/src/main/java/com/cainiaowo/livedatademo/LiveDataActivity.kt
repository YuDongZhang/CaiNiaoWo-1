package com.cainiaowo.livedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cainiaowo.livedatademo.databinding.ActivityLivedataBinding

class LiveDataActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LiveDataActivity"
    }

    private lateinit var binding: ActivityLivedataBinding

    // 声明LiveData变量
    val liveData = MutableLiveData<String>()

    // 转换成我们需要的LiveData变量
    val mappedLiveData = liveData.map {
        it.takeLast(6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLivedataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 创建Fragment
        val liveDataFragment = LiveDataFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, liveDataFragment)
            .commit()
        // TODO NOTE: fragment的hide和show不会改变fragment的生命周期状态,可以用attach和detach
        // attach fragment
        binding.btnShowFg.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .attach(liveDataFragment)
                .commit()
        }
        // detach fragment
        binding.btnHideFg.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .detach(liveDataFragment)
                .commit()
        }

        binding.btnChangeLivedata.setOnClickListener {
            liveData.value = System.currentTimeMillis().toString()
        }


        // 观察
        liveData.observe(this) {
            binding.tvLiveDataActivity.text = "当前LiveData的值：$it"
            Log.d(TAG, "LiveData在Activity中的值：$it")
        }
        mappedLiveData.observe(this) {
            binding.tvMappedDataActivity.text = "Map后LiveData值:$it"
            Log.d(TAG, "MappedLiveData在Activity中的值：$it")
        }
    }
}