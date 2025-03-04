package com.whatever.caramel.feat.content.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.content.ContentRoute
import kotlinx.serialization.Serializable

@Serializable
data object ContentRoute

fun NavHostController.navigateToContent(navOptions: NavOptions? = null) {
    navigate(
        route = ContentRoute,
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