package com.whatever.caramel.feature.main.navigation

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
    navigateToTodoDetail: (Long, ContentType) -> Unit,
    navigateToCreateTodo: () -> Unit,
) {
    composable<MainRoute>() {
        MainRoute(
            navigateToSetting = navigateToSetting,
            navigateToStaredCoupleDay = navigateToStaredCoupleDay,
            navigateToTodoDetail = navigateToTodoDetail,
            navigateToCreateTodo = navigateToCreateTodo
        )
    }
}