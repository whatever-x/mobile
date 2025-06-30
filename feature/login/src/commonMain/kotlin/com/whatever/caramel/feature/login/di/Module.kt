package com.whatever.caramel.feature.login.di

import com.whatever.caramel.feature.login.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginFeatureModule =
    module {
        viewModelOf(::LoginViewModel)
    }

expect val socialModule: Module
