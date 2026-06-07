package com.whatever.caramel.feature.balancegame.share.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import com.whatever.caramel.feature.balancegame.share.BalanceGameShareRoute as BalanceGameShareScreenRoute

@Serializable
data class BalanceGameShareRoute(
    val gameId: Long,
    val question: String,
    val myNickname: String,
    val myGender: String,
    val myChoice: String,
    val partnerNickname: String,
    val partnerGender: String,
    val partnerChoice: String,
)

fun NavController.navigateToBalanceGameShare(
    gameId: Long,
    question: String,
    myNickname: String,
    myGender: String,
    myChoice: String,
    partnerNickname: String,
    partnerGender: String,
    partnerChoice: String,
    navOptions: NavOptions? = null,
) {
    navigate(
        route =
            BalanceGameShareRoute(
                gameId = gameId,
                question = question,
                myNickname = myNickname,
                myGender = myGender,
                myChoice = myChoice,
                partnerNickname = partnerNickname,
                partnerGender = partnerGender,
                partnerChoice = partnerChoice,
            ),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.balanceGameShareScreen(navigateToBack: () -> Unit) {
    composable<BalanceGameShareRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
    ) {
        BalanceGameShareScreenRoute(
            navigateToBack = navigateToBack,
        )
    }
}
