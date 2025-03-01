package com.whatever.caramel.feat.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(
        route = LoginRoute,
        navOptions = navOptions
    )

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