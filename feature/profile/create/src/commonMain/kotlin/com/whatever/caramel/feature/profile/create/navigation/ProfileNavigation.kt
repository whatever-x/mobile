package com.whatever.caramel.feature.profile.create.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.user.UserStatus
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
    navigateToStartDestination: (UserStatus) -> Unit,
    showErrorToast: (String) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    composable<ProfileCreateRoute>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 300),
            )
        },
    ) {
        ProfileCreateRoute(
            navigateToLogin = navigateToLogin,
            navigateToStartDestination = navigateToStartDestination,
            showErrorToast = showErrorToast,
            showErrorDialog = showErrorDialog,
        )
    }
}
