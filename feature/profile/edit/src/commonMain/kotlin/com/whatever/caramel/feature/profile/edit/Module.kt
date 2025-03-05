package com.whatever.caramel.feature.profile.edit

import org.koin.dsl.module

val profileEditFeatureModule = module {
    viewModelOf(::ProfileEditViewModel)
}