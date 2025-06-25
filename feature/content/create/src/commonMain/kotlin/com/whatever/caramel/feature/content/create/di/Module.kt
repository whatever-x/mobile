package com.whatever.caramel.feature.content.create.di

import com.whatever.caramel.feature.content.create.ContentCreateViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentCreateFeatureModule = module {
    viewModelOf(::ContentCreateViewModel)
}