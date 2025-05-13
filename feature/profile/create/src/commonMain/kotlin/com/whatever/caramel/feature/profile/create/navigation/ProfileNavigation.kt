package com.whatever.caramel.feature.profile.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.profile.create.ProfileCreateRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileCreateRoute

fun NavController.navigateToCreateProfile(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = ProfileCreateRoute) {
        builder()
    }
}

fun NavGraphBuilder.createProfileScreen(
    navigateToLogin: () -> Unit,
    navigateToStartDestination: () -> Unit
) {
    composable<ProfileCreateRoute>() {
        ProfileCreateRoute(
            navigateToLogin = navigateToLogin,
            navigateToStartDestination = navigateToStartDestination
        )
    }
}
