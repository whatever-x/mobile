package com.whatever.caramel.feat.main.presentation.calendar.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.main.presentation.calendar.CalendarRoute
import kotlinx.serialization.Serializable

@Serializable
data object CalendarRoute

fun NavGraphBuilder.calendarContent(
    navigateToCreateTodo: () -> Unit,
    navigateToTodoDetail: () -> Unit,
) {
    composable<CalendarRoute> {
        CalendarRoute(
            navigateToCreateTodo = navigateToCreateTodo,
            navigateToTodoDetail = navigateToTodoDetail,
        )
    }
}