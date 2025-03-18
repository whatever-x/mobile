package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.feature.calendar.component.CalendarDatePicker
import com.whatever.caramel.feature.calendar.component.CalendarDayOfWeek
import com.whatever.caramel.feature.calendar.component.CalendarYearText
import com.whatever.caramel.feature.calendar.component.CaramelCalendar
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.coroutines.launch

@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit,
) {
    val initYear = rememberSaveable { state.selectedYear }
    val initMonth = rememberSaveable { state.selectedMonth }
    val initPage = rememberSaveable { state.currentPage }

    val calendarPageState = rememberPagerState(
        initialPage = initPage,
        pageCount = { state.totalPage }
    )

    LaunchedEffect(Unit) {
        snapshotFlow { calendarPageState.currentPage }
            .collect { page ->
                onIntent(
                    CalendarIntent.SwipeCalendar(
                        pageIndex = page,
                        isInitPage = page == initPage
                    )
                )
            }
    }

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CaramelTheme.color.background.primary)
    ) {
        CalendarYearText(
            modifier = Modifier
                .padding(start = CaramelTheme.spacing.xl)
                .height(52.dp),
            state = state.datePickerState,
            onClickDate = { year, month ->
                onIntent(CalendarIntent.ToggleDate(year, month))
            }
        )
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f)
            ) {
                CalendarDayOfWeek()
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = calendarPageState,
                    beyondViewportPageCount = 3,
                ) {
                    CaramelCalendar(
                        state = state,
                        onClickDay = { day ->
                            onIntent(
                                CalendarIntent.ToggleDay(
                                    month = state.selectedMonth,
                                    dayList = state.calendarDays,
                                    selectedDay = day
                                )
                            )
                        }
                    )
                }
            }
            if (state.datePickerState.isOpen) {
                CalendarDatePicker(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f),
                    state = state.datePickerState,
                    space = 10,
                    onClickDatePickerBackground = { year, month ->
                        scope.launch {
                            calendarPageState.scrollToPage(
                                (year - CalendarModel.MIN_YEAR).times(CalendarModel.MONTH_RANGE.count()) + (month - 1)
                            )
                        }
                        onIntent(
                            CalendarIntent.DismissDatePicker(
                                year = year,
                                month = month,
                                isInitPage = (year == initYear) && (month == initMonth)
                            )
                        )
                    }
                )
            }
        }
    }
}


