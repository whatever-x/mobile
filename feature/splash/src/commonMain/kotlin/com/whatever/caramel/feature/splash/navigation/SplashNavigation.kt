package com.whatever.caramel.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
    navigateToCreateProfile: () -> Unit,
    navigateToInviteCouple : () -> Unit
) {
    composable<SplashRoute>() {
        SplashRoute(
            navigateToLogin = navigateToLogin,
            navigateToMain = navigateToMain,
            navigateToCreateProfile = navigateToCreateProfile,
            navigateToInviteCouple = navigateToInviteCouple
        )
    }
}