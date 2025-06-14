package com.whatever.caramel.feature.login.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.user.UserStatus
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
    navigateToStartDestination: (UserStatus) -> Unit
) {
    composable<LoginRoute>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        LoginRoute(
            navigateToStartDestination = navigateToStartDestination
        )
    }
}