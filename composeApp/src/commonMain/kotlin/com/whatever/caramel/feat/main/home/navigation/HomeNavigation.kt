package com.whatever.caramel.feat.main.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.main.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavHostController.navigateToHome(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = HomeRoute,
        builder = builder
    )
}

fun NavGraphBuilder.homeContent(
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: () -> Unit,
    navigateToCreateTodo: () -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            navigateToSetting = navigateToSetting,
            navigateToStaredCoupleDay = navigateToStaredCoupleDay,
            navigateToTodoDetail = navigateToTodoDetail,
            navigateToCreateTodo = navigateToCreateTodo
        )
    }
}