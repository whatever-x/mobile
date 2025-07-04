package com.whatever.caramel.feature.copule.connecting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.copule.connecting.CoupleConnectingRoute
import kotlinx.serialization.Serializable

@Serializable
data object ConnectingRoute

fun NavController.navigateToConnectingCouple(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = ConnectingRoute) {
        popUpTo(graph.id)
        builder()
    }
}

fun NavGraphBuilder.connectingScreen(navigateToMain: () -> Unit) {
    composable<ConnectingRoute> {
        CoupleConnectingRoute(
            navigateToMain = navigateToMain,
        )
    }
}
