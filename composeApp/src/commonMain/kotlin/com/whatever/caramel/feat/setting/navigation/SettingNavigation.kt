package com.whatever.caramel.feat.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.setting.SettingRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(
        route = SettingRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.settingScreen(
    navigateToHome: () -> Unit,
    navigateToEditBirthday: () -> Unit,
    navigateToEditNickName: () -> Unit,
) {
    composable<SettingRoute> {
        SettingRoute(
            navigateToHome = navigateToHome,
            navigateToEditBirthday = navigateToEditBirthday,
            navigateToEditNickName = navigateToEditNickName
        )
    }
}