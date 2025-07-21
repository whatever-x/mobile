package com.whatever.caramel.feature.splash.di

import com.whatever.caramel.feature.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashFeatureModule =
    module {
        viewModelOf(::SplashViewModel)
    }
