package com.whatever.caramel.feature.calendar.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.feature.calendar.CalendarRoute
import kotlinx.serialization.Serializable

@Serializable
data object CalendarRoute

fun NavHostController.navigateToCalendar(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = CalendarRoute,
        builder = builder
    )
}

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