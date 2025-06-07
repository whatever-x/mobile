package com.whatever.caramel.feature.content.create.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.content.create.ContentCreateRoute
import kotlinx.serialization.Serializable

@Serializable
data class ContentCreateRoute(
    val contentId: Long
)

fun NavHostController.navigateToContentCreate(navOptions: NavOptions? = null, contentId: Long) {
    navigate(
        route = ContentCreateRoute(
            contentId = contentId
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentCreateScreen(
    navigateToBackStack: () -> Unit
) {
    composable<ContentCreateRoute>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        ContentCreateRoute(
            navigateToBackStack = navigateToBackStack
        )
    }
}