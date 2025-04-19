package com.whatever.caramel.feature.copule.invite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.copule.invite.CoupleInviteRoute
import kotlinx.serialization.Serializable

@Serializable
data object CoupleInviteRote

fun NavController.navigateToInviteCouple(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = CoupleInviteRote) {
        popUpTo(graph.id)
        builder()
    }
}

fun NavGraphBuilder.inviteCoupleScreen(
    navigateToConnectCouple: () -> Unit,
    navigateToLogin: () -> Unit
) {
    composable<CoupleInviteRote>() {
        CoupleInviteRoute(
            navigateToConnectCouple = navigateToConnectCouple,
            navigateToLogin = navigateToLogin
        )
    }
}