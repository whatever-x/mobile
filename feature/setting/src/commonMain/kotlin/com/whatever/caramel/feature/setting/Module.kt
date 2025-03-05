package com.whatever.caramel.feature.setting

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingFeatureModule = module {
    viewModelOf(::SettingViewModel)
}