package com.whatever.caramel.feat.couple.code.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.couple.code.CoupleConnectRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConnectCoupleRoute

fun NavHostController.navigateToConnectCouple(navOptions: NavOptions? = null) {
    navigate(
        route = ConnectCoupleRoute,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.connectCoupleScreen(
    navigateToMain: () -> Unit,
    navigateToInviteCouple: () -> Unit
) {
    composable<ConnectCoupleRoute>() {
        CoupleConnectRoute(
            navigateToMain = navigateToMain,
            navigateToInviteCouple = navigateToInviteCouple
        )
    }
}