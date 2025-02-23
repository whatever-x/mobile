package com.whatever.caramel.feat.splash.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.splash.presentation.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

fun NavGraphBuilder.splashScreen(
    navigateToOnBoarding: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    composable<SplashRoute>() {
        SplashRoute(
            navigateToOnBoarding = navigateToOnBoarding,
            navigateToLogin = navigateToLogin,
            navigateToMain = navigateToMain
        )
    }
}