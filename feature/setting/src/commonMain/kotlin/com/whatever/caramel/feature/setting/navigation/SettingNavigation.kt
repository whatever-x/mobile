package com.whatever.caramel.feature.setting.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.setting.SettingRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(
        route = SettingRoute,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.settingScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToEditCountDown: (String) -> Unit,
    navigateToEditBirthday: (String) -> Unit,
    navigateToEditNickName: (String) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
    showErrorToast: (String) -> Unit,
) {
    composable<SettingRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
    ) {
        SettingRoute(
            navigateToHome = navigateToHome,
            navigateToEditBirthday = navigateToEditBirthday,
            navigateToEditNickName = navigateToEditNickName,
            navigateToEditCountDown = navigateToEditCountDown,
            navigateToLogin = navigateToLogin,
            showErrorDialog = showErrorDialog,
            showErrorToast = showErrorToast,
        )
    }
}
