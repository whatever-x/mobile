package com.whatever.caramel.feature.content.edit.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.edit.ContentEditRoute
import kotlinx.serialization.Serializable

@Serializable
data class ContentEditScreenRoute(
    val id: Long,
    val type: String,
)

fun NavHostController.navigateToContentEdit(
    id: Long,
    type: ContentType,
    navOptions: NavOptions? = null,
) {
    navigate(
        route = ContentEditScreenRoute(id = id, type = type.name),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.contentEditScreen(
    popBackStack: () -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    composable<ContentEditScreenRoute>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(400),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(400),
            )
        },
    ) { _ ->
        // backStackEntry can be used if needed
        ContentEditRoute(
            popBackStack = popBackStack,
            showErrorDialog = showErrorDialog,
        )
    }
}
