package com.whatever.caramel.feature.copule.invite

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val coupleInviteFeatureModule = module {
    viewModelOf(::CoupleInviteViewModel)
}