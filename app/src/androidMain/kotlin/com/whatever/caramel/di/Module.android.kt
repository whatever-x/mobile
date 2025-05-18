package com.whatever.caramel.di

import com.whatever.caramel.app.CaramelViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val appViewModelModule: Module
    get() = module {
        viewModelOf(::CaramelViewModel)
    }