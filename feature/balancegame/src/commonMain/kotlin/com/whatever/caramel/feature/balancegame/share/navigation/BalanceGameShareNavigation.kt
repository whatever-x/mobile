package com.whatever.caramel.feature.balancegame.share.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.balancegame.share.BalanceGameShareRoute
import kotlinx.serialization.Serializable

@Serializable
data class BalanceGameShareRoute(
    val question: String,
    val myChoice: String,
    val partnerChoice: String,
    val myGender: String,
    val partnerGender: String,
    val isSameChoice: Boolean,
)

fun NavController.navigateToBalanceGameShare(
    question: String,
    myChoice: String,
    partnerChoice: String,
    myGender: Gender,
    partnerGender: Gender,
    isSameChoice: Boolean,
    navOptions: NavOptions? = null,
) {
    navigate(
        route =
            BalanceGameShareRoute(
                question = question,
                myChoice = myChoice,
                partnerChoice = partnerChoice,
                myGender = myGender.name,
                partnerGender = partnerGender.name,
                isSameChoice = isSameChoice,
            ),
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
