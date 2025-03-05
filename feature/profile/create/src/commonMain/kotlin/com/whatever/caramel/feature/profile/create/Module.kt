package com.whatever.caramel.feature.profile.create

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileCreateFeatureModule = module {
    viewModelOf(::ProfileCreateViewModel)
}