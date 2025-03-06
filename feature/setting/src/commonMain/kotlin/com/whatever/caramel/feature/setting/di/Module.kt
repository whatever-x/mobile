package com.whatever.caramel.feature.setting.di

import com.whatever.caramel.feature.setting.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingFeatureModule = module {
    viewModelOf(::SettingViewModel)
}