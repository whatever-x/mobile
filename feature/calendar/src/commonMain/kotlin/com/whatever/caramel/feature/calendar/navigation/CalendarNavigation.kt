package com.whatever.caramel.feature.calendar.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.calendar.CalendarRoute
import kotlinx.serialization.Serializable

@Serializable
data object CalendarRoute

fun NavHostController.navigateToCalendar(builder: NavOptionsBuilder.() -> Unit) {
    navigate(
        route = CalendarRoute,
        builder = builder,
    )
}

fun NavGraphBuilder.calendarContent(
    navigateToCreateSchedule: (ContentType, String) -> Unit,
    navigateToScheduleDetail: (Long, ContentType) -> Unit,
    showErrorToast: (String) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    composable<CalendarRoute> {
        CalendarRoute(
            navigateToCreateSchedule = navigateToCreateSchedule,
            navigateToScheduleDetail = navigateToScheduleDetail,
            showErrorToast = showErrorToast,
            showErrorDialog = showErrorDialog,
        )
    }
}
