package com.whatever.caramel.feature.balancegame.history.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.balancegame.history.BalanceGameHistoryRoute
import kotlinx.serialization.Serializable

@Serializable
data object BalanceGameHistoryRoute

fun NavController.navigateToBalanceGameHistory(navOptions: NavOptions? = null) {
    navigate(
        route = BalanceGameHistoryRoute,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.balanceGameHistoryScreen(
    navigateToBack: () -> Unit,
    navigateToShare: (
        question: String,
        myChoice: String,
        partnerChoice: String,
        myGender: Gender,
        partnerGender: Gender,
        isSameChoice: Boolean,
    ) -> Unit,
) {
    composable<BalanceGameHistoryRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
    ) {
        BalanceGameHistoryRoute(
            navigateToBack = navigateToBack,
            navigateToShare = navigateToShare,
        )
    }
}
