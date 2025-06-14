package com.whatever.caramel.feature.content.create.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.create.ContentCreateRoute
import kotlinx.serialization.Serializable

@Serializable
data class ContentCreateRoute(
    val contentType: ContentType
)

fun NavHostController.navigateToContentCreate(
    navOptions: NavOptions? = null,
    contentType: ContentType
) {
    navigate(
        route = ContentCreateRoute(
            contentType = contentType
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentCreateScreen(
    navigateToBackStack: () -> Unit
) {
    composable<ContentCreateRoute>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(400)
            )
        }
    ) {
        ContentCreateRoute(
            navigateToBackStack = navigateToBackStack
        )
    }
}