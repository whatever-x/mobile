package com.whatever.caramel.feature.content.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.content.ContentRoute
import kotlinx.serialization.Serializable

@Serializable
data class ContentRoute(
    val contentId: Long
)

fun NavHostController.navigateToContent(navOptions: NavOptions? = null, contentId: Long) {
    navigate(
        route = ContentRoute(
            contentId = contentId
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentScreen(
    navigateToBackStack: () -> Unit
) {
    composable<ContentRoute>() {
        ContentRoute(
            navigateToBackStack = navigateToBackStack
        )
    }
}