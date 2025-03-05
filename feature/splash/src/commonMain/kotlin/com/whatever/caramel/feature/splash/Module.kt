package com.whatever.caramel.feature.splash

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashFeatureModule = module {
    viewModelOf(::SplashViewModel)
}