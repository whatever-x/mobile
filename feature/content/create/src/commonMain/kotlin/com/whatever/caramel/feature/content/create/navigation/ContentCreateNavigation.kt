package com.whatever.caramel.feature.content.create.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        ContentCreateRoute(
            navigateToBackStack = navigateToBackStack
        )
    }
}