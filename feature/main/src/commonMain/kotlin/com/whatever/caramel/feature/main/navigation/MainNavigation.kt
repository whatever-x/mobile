package com.whatever.caramel.feature.main.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.main.MainRoute
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavController.navigateToMain(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = MainRoute) {
        popUpTo(graph.id)
        builder()
    }
}

fun NavGraphBuilder.mainGraph(
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToScheduleDetail: (Long, ContentType) -> Unit,
    navigateToCreateMemo: (ContentType) -> Unit,
    navigateToCreateSchedule: (ContentType, String) -> Unit,
    showErrorToast: (String) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    composable<MainRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        MainRoute(
            navigateToSetting = navigateToSetting,
            navigateToStaredCoupleDay = navigateToStaredCoupleDay,
            navigateToScheduleDetail = navigateToScheduleDetail,
            navigateToCreateMemo = navigateToCreateMemo,
            navigateToCreateSchedule = navigateToCreateSchedule,
            showErrorToast = showErrorToast,
            showErrorDialog = showErrorDialog,
        )
    }
}
