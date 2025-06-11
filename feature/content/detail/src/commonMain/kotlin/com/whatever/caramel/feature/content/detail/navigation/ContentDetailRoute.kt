package com.whatever.caramel.feature.content.detail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.detail.ContentDetailRoute
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailRoute(val contentId: Long, val type: String)

fun NavHostController.navigateToContentDetail(
    contentId: Long,
    type: ContentType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = ContentDetailRoute(contentId = contentId, type = type.name),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentDetailScreen(
    popBackStack: () -> Unit,
    navigateToEdit: (Long, ContentType) -> Unit,
) {
    composable<ContentDetailRoute>(
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(600)
            )
        }
    ) { _ -> // backStackEntry can be used if needed
        ContentDetailRoute(
            popBackStack = popBackStack,
            navigateToEdit = navigateToEdit
        )
    }
}