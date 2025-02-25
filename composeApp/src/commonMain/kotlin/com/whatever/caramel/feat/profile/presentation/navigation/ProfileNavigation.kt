package com.whatever.caramel.feat.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.profile.presentation.create.ProfileCreateRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileCreateRoute

fun NavController.navigateToCreateProfile(navOptions: NavOptions? = null) {
    navigate(
        route = ProfileCreateRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.createProfileScreen(
    navigateToLogin: () -> Unit,
    navigateToConnectCouple: () -> Unit
) {
    composable<ProfileCreateRoute>() {
        ProfileCreateRoute(
            navigateToLogin = navigateToLogin,
            navigateToConnectCouple = navigateToConnectCouple
        )
    }
}
