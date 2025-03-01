package com.whatever.caramel.feat.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.onboarding.OnboardingRoute
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingRoute

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) =
    navigate(
        route = OnboardingRoute,
        navOptions = navOptions
    )

fun NavGraphBuilder.onboardingScreen(
    navigateToLogin: () -> Unit,
) {
    composable<OnboardingRoute>() {
        OnboardingRoute(
            navigateToLogin = navigateToLogin,
        )
    }
}