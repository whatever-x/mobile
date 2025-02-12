package com.whatever.caramel.feature.onboarding

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route

val onboardingNavigator = screenModule {
    register<Route.Onboarding> {
        OnboardingScreenRoot()
    }
}