package com.cainiaowo.viewmodeldemo

import android.util.Log
import androidx.lifecycle.ViewModel

class NoParamViewModel : ViewModel() {
    companion object {
        const val TAG = "NoParamViewModel"
    }

    init {
        Log.e(TAG, "创建NoParamViewModel")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared: 销毁NoParamViewModel" )
    }
}