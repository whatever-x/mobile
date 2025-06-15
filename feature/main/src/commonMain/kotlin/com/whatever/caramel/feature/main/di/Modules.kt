package com.whatever.caramel.feature.main.di

import com.whatever.caramel.feature.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::MainViewModel)
}