package com.whatever.caramel.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.user.UserStatus
import com.whatever.caramel.feature.splash.SplashRoute
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute

fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit,
    navigateToStartDestination: (UserStatus) -> Unit
) {
    composable<SplashRoute> {
        SplashRoute(
            navigateToLogin = navigateToLogin,
            navigateToStartDestination = navigateToStartDestination
        )
    }
}