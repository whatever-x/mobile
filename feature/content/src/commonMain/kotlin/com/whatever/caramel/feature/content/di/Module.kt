package com.whatever.caramel.feature.content.di

import com.whatever.caramel.feature.content.ContentViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentFeatureModule = module {
    viewModelOf(::ContentViewModel)
}