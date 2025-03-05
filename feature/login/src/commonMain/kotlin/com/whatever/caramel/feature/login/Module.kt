package com.whatever.caramel.feature.login

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginFeatureModule = module {
    viewModelOf(::LoginViewModel)
}