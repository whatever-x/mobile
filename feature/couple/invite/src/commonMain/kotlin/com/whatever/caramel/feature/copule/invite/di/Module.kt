package com.whatever.caramel.feature.copule.invite.di

import com.whatever.caramel.feature.copule.invite.CoupleInviteViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val shareServiceModule: Module

val coupleInviteFeatureModule = module {
    viewModelOf(::CoupleInviteViewModel)
}