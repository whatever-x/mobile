package com.whatever.caramel.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = LoginRoute) {
        popUpTo(graph.id)
        builder()
    }
}

fun NavGraphBuilder.loginScreen(
    navigateToCreateProfile: () -> Unit,
    navigateToConnectCouple: () -> Unit,
) {
    composable<LoginRoute>() {
        LoginRoute(
            navigateToConnectCouple = navigateToConnectCouple,
            navigateToCreateProfile = navigateToCreateProfile
        )
    }
}