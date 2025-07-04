package com.whatever.caramel.feature.profile.edit.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.profile.edit.ProfileEditRoute
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEditRoute(
    val editType: String,
    val nickname: String,
    val birthday: String,
    val startDate: String,
)

fun NavHostController.navigateToEditProfile(
    editType: ProfileEditType,
    nickname: String = "",
    birthday: String = "",
    startDate: String = "",
    navOptions: NavOptions? = null,
) {
    navigate(
        route =
            ProfileEditRoute(
                editType = editType.name,
                nickname = nickname,
                birthday = birthday,
                startDate = startDate,
            ),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.editProfileScreen(
    popBackStack: () -> Unit,
    showErrorDialog: (title: String, message: String?) -> Unit,
    showErrorToast: (message: String) -> Unit,
) {
    composable<ProfileEditRoute>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(400),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(400),
            )
        },
    ) {
        ProfileEditRoute(
            popBackStack = popBackStack,
            showErrorDialog = showErrorDialog,
            showErrorToast = showErrorToast,
        )
    }
}
