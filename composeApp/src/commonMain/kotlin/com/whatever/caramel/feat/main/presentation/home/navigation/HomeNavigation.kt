package com.whatever.caramel.feat.main.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.main.presentation.home.HomeRoute
import com.whatever.caramel.feat.main.presentation.navigation.MainNavGraph

fun NavGraphBuilder.homeContent(
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: () -> Unit,
    navigateToCreateTodo: () -> Unit,
) {
    composable<MainNavGraph.Home> {
        HomeRoute(
            navigateToSetting = navigateToSetting,
            navigateToStaredCoupleDay = navigateToStaredCoupleDay,
            navigateToTodoDetail = navigateToTodoDetail,
            navigateToCreateTodo = navigateToCreateTodo
        )
    }
}