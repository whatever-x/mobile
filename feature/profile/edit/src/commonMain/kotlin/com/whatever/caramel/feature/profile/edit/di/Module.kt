package com.whatever.caramel.feature.profile.edit.di

import com.whatever.caramel.feature.profile.edit.ProfileEditViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileEditFeatureModule =
    module {
        viewModelOf(::ProfileEditViewModel)
    }
