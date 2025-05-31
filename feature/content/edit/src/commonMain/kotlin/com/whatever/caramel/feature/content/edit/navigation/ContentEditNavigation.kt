package com.whatever.caramel.feature.content.edit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.content.edit.ContentEditRoute
import kotlinx.serialization.Serializable

enum class ContentType {
    MEMO,
    CALENDAR
}

@Serializable
data class ContentEditScreenRoute(val id: Long, val type: String)

fun NavHostController.navigateToContentEdit(
    id: Long,
    type: ContentType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = ContentEditScreenRoute(id = id, type = type.name),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contentEditScreen(
    popBackStack: () -> Unit
) {
    composable<ContentEditScreenRoute> { _ -> // backStackEntry can be used if needed
        ContentEditRoute(
            popBackStack = popBackStack
        )
    }
} 