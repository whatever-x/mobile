package com.whatever.caramel.feat.couple.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.whatever.caramel.feat.couple.presentation.code.CoupleCodeRoute
import com.whatever.caramel.feat.couple.presentation.invite.CoupleInviteRoute
import kotlinx.serialization.Serializable

sealed interface CoupleGraph {

    @Serializable
    data object Route

    @Serializable
    data object Invite

    @Serializable
    data object Code

}

fun NavController.navigateToCoupleInvite(navOptions: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = CoupleGraph.Invite,
        builder = navOptions
    )
}

fun NavGraphBuilder.coupleGraph(
    navHostController: NavHostController,
    navigateToMain: () -> Unit
) {
    navigation<CoupleGraph.Route>(
        startDestination = CoupleGraph.Invite
    ) {
        composable<CoupleGraph.Invite>() {
            CoupleInviteRoute(
                navigateToCoupleCode = {
                    navHostController.navigate(CoupleGraph.Code)
                }
            )
        }

        composable<CoupleGraph.Code>() {
            CoupleCodeRoute(
                navigateToHome = navigateToMain
            )
        }
    }
}