package com.cainiaowo.viewmodeldemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParamViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val TAG = "ParamViewModel"
    }

    init {
        Log.e(TAG, "创建ParamViewModel")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e(TAG, "onCleared: 销毁ParamViewModel")
    }
}

class ParamFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ParamViewModel(application) as T
    }
}