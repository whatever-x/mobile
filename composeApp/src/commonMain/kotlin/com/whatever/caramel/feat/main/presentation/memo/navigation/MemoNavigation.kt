package com.whatever.caramel.feat.main.presentation.memo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.main.presentation.memo.MemoRoute
import kotlinx.serialization.Serializable

@Serializable
data object MemoRoute

fun NavHostController.navigateToMemo(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = MemoRoute,
        builder = builder
    )
}

fun NavGraphBuilder.memoContent(
    navigateToTodoDetail: () -> Unit
) {
    composable<MemoRoute> {
        MemoRoute(
            navigateToTodoDetail = navigateToTodoDetail
        )
    }
}