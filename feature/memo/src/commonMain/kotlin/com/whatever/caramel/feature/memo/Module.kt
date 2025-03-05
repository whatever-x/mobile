package com.whatever.caramel.feature.memo

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memoFeatureModule = module {
    viewModelOf(::MemoViewModel)
}

