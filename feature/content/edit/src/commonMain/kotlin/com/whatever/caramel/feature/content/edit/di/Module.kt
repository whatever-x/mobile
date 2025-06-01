package com.whatever.caramel.feature.content.edit.di

import com.whatever.caramel.feature.content.edit.ContentEditViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentEditFeatureModule: Module = module {
    viewModelOf(::ContentEditViewModel)
} 