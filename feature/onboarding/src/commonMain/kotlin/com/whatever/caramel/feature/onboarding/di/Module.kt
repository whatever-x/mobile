package com.whatever.caramel.feature.onboarding.di

import com.whatever.caramel.feature.onboarding.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingFeatureModule = module {
    viewModelOf(::OnboardingViewModel)
}