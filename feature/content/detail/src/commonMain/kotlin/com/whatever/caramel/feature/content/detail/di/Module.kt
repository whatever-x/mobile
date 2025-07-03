package com.whatever.caramel.feature.content.detail.di

import com.whatever.caramel.feature.content.detail.ContentDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentDetailFeatureModule =
    module {
        viewModelOf(::ContentDetailViewModel)
    }
