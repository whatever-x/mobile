package com.whatever.caramel.feature.home.di

import com.whatever.caramel.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeFeatureModule =
    module {
        viewModelOf(::HomeViewModel)
    }
