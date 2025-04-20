package com.whatever.caramel.feature.couple.connect.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.couple.connect.CoupleConnectRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConnectCoupleRoute

fun NavHostController.navigateToConnectCouple(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = ConnectCoupleRoute) {
        builder()
    }
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