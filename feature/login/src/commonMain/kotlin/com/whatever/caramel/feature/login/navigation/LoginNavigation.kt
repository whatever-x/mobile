package com.whatever.caramel.feature.login.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = LoginRoute) {
        builder()
    }
}

fun NavGraphBuilder.loginScreen(
    navigateToStartDestination: () -> Unit
) {
    composable<LoginRoute>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 300)
            )
        },
        exitTransition = { ExitTransition.None },
    ) {
        LoginRoute(
            navigateToStartDestination = navigateToStartDestination
        )
    }
}