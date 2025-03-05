package com.whatever.caramel.feature.couple.connect

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coupleConnectFeatureModule = module {
    viewModelOf(::CoupleConnectViewModel)
}