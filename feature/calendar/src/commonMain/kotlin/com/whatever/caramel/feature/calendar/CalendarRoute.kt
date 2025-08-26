package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.policy.CalendarPolicy.TOTAL_MONTH_SIZE
import com.whatever.caramel.core.domain.policy.CalendarPolicy.TOTAL_YEAR_SIZE
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = koinViewModel(),
    navigateToCreateSchedule: (ContentType, String) -> Unit,
    navigateToScheduleDetail: (Long, ContentType) -> Unit,
    showErrorToast: (String) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current
    val horizontalPagerState: PagerState =
        rememberPagerState(initialPage = state.page) { TOTAL_YEAR_SIZE * TOTAL_MONTH_SIZE }


    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CalendarSideEffect.NavigateToScheduleDetail -> navigateToScheduleDetail(sideEffect.id, sideEffect.contentType)
                is CalendarSideEffect.NavigateToAddSchedule -> navigateToCreateSchedule(ContentType.CALENDAR, sideEffect.dateTimeString)
                is CalendarSideEffect.OpenWebView -> uriHandler.openUri(sideEffect.url)
                is CalendarSideEffect.ShowErrorDialog -> showErrorDialog(sideEffect.message, sideEffect.description)
                is CalendarSideEffect.ShowErrorToast -> showErrorToast(sideEffect.message)
                is CalendarSideEffect.ScrollToPage -> horizontalPagerState.scrollToPage(page = state.page)
            }
        }
    }

//    ObserveLifecycleEvent { event ->
//        if (event == Lifecycle.Event.ON_RESUME) {
//            viewModel.intent(CalendarIntent.Initialize)
//        }
//    }

    CalendarScreen(
        state = state,
        horizontalPagerState = horizontalPagerState,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
