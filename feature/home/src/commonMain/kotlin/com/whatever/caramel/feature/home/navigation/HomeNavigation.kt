package com.whatever.caramel.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavHostController.navigateToHome(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = HomeRoute,
        builder = builder,
    )
}

fun NavGraphBuilder.homeContent(
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: (Long, ContentType) -> Unit,
    navigateToCreateTodo: (ContentType) -> Unit,
    navigateToBalanceGameHistory: () -> Unit,
    navigateToBalanceGameShare: (
        gameId: Long,
        question: String,
        myNickname: String,
        myGender: String,
        myChoice: String,
        partnerNickname: String,
        partnerGender: String,
        partnerChoice: String,
    ) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
    showErrorToast: (String) -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            navigateToSetting = navigateToSetting,
            navigateToStaredCoupleDay = navigateToStaredCoupleDay,
            navigateToTodoDetail = navigateToTodoDetail,
            navigateToCreateTodo = navigateToCreateTodo,
            navigateToBalanceGameHistory = navigateToBalanceGameHistory,
            navigateToBalanceGameShare = navigateToBalanceGameShare,
            showErrorDialog = showErrorDialog,
            showErrorToast = showErrorToast,
        )
    }
}
