package com.whatever.caramel.feature.content

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentFeatureModule = module {
    viewModelOf(::ContentViewModel)
}