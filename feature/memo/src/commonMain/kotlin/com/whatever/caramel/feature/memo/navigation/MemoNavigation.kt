package com.whatever.caramel.feature.memo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.memo.MemoRoute
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
    navigateToTodoDetail: (Long, ContentType) -> Unit,
) {
    composable<MemoRoute> {
        MemoRoute(
            navigateToTodoDetail = navigateToTodoDetail
        )
    }
}