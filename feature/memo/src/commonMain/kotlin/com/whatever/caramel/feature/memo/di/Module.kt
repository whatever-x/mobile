package com.whatever.caramel.feature.memo.di

import com.whatever.caramel.feature.memo.MemoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memoFeatureModule = module {
    viewModelOf(::MemoViewModel)
}

