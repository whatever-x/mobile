package com.whatever.caramel.error

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDialogRoute(
    val message : String,
    val description : String? = null
)

fun NavHostController.navigateToErrorDialog(
    builder: NavOptionsBuilder.() -> Unit = {},
    message : String,
    description : String? = null
) {
    navigate(
        route = ErrorDialogRoute(
            message = message,
            description = description
        ),
        builder = builder
    )
}

fun NavGraphBuilder.errorDialogContent(
    popBackStack : () -> Unit
) {
    composable<ErrorDialogRoute> { backStackEntry ->
        val argument = backStackEntry.toRoute<ErrorDialogRoute>()
        ErrorDialog(
            title = argument.message,
            message = argument.description,
            popBackStack = { popBackStack() }
        )
    }
}