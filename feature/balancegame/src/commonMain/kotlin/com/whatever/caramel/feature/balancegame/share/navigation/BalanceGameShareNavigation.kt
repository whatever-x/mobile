package com.whatever.caramel.feature.balancegame.share.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.balancegame.share.BalanceGameShareRoute
import kotlinx.serialization.Serializable

@Serializable
data object BalanceGameShareRoute

fun NavController.navigateToBalanceGameShare(navOptions: NavOptions? = null) {
    navigate(
        route = BalanceGameShareRoute,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.balanceGameShareScreen(navigateToBack: () -> Unit) {
    composable<BalanceGameShareRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
    ) {
        BalanceGameShareRoute(
            navigateToBack = navigateToBack,
        )
    }
}
