package com.whatever.caramel.feature.couple.connect.di

import com.whatever.caramel.feature.couple.connect.CoupleConnectViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coupleConnectFeatureModule =
    module {
        viewModelOf(::CoupleConnectViewModel)
    }
