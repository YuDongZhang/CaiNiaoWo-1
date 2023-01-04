package com.cainiaowo.mine

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * mine模块相关的koin的module配置
 */
val moduleMine = module {

    viewModel { MineViewModel() }

}