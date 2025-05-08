package com.whatever.caramel.feature.copule.connecting.di

import com.whatever.caramel.feature.copule.connecting.CoupleConnectingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coupleConnectingFeatureModule = module {
    viewModelOf(::CoupleConnectingViewModel)
}