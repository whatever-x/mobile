package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.component.CalendarDayOfWeek
import com.whatever.caramel.feature.calendar.component.CalendarYearWithDropDown
import com.whatever.caramel.feature.calendar.component.CaramelCalendar
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.coroutines.launch

// FIXME : 공통질문, 모든 컴포넌트는 재사용을 감안해서 적용해야할까? (dp나.. 컬러나..)
@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit,
) {
    val calendarPageState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.totalPage }
    )

    LaunchedEffect(calendarPageState.currentPage) {
        onIntent(CalendarIntent.SwipeCalendar(calendarPageState.currentPage))
    }

    val pagerScrollCoroutine = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CaramelTheme.color.background.primary)
    ) {
        CalendarYearWithDropDown(
            modifier = Modifier
                .padding(start = CaramelTheme.spacing.xl)
                .height(52.dp),
            state = state.datePickerState,
            onClickDate = { year, month ->
                onIntent(CalendarIntent.ToggleDate(year, month))
            },
            onClickDatePickerBackground = { year, month ->
                // FIXME : onIntent가 실행 -> State가 업데이트 -> 자동 스크롤은 안되는걸까?
                onIntent(CalendarIntent.DismissDatePicker(year, month))
                pagerScrollCoroutine.launch {
                    calendarPageState.animateScrollToPage(state.currentPage)
                }
            }
        )
        CalendarDayOfWeek()
        HorizontalPager(
            state = calendarPageState,
            beyondViewportPageCount = 3,
        ) {
            CaramelCalendar(
                state = state,
                onClickDay = { day ->
                    onIntent(
                        CalendarIntent.ToggleDay(
                            month = state.selectedMonth!!,
                            dayList = state.calendarDays,
                            selectedDay = day
                        )
                    )
                }
            )
        }
    }
}


