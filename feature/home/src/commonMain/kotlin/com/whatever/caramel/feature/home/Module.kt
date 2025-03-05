package com.whatever.caramel.feature.home

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeFeatureModule = module {
    viewModelOf(::HomeViewModel)
}