package com.whatever.caramel.feature.calendar

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.domain.model.calendar.CalendarModel.Companion.createSampleCalendarModel
import com.whatever.caramel.feature.calendar.component.CalendarDatePicker
import com.whatever.caramel.feature.calendar.component.CalendarYear
import com.whatever.caramel.feature.calendar.component.CaramelCalendar
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit
) {
    // 현재 날짜로 캘린더 모델 생성

    val currentDate by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    }
    var initialYear by remember {
        mutableStateOf(currentDate.year)
    }
    var initialMonth by remember {
        mutableStateOf(currentDate.monthNumber)
    }

    // 전체 페이지
    val totalPages = 201 * 12
    Napier.d { "initYear = $initialYear, initialMonth = $initialMonth" }
    val initPage = (initialYear - 1900) * 12 + (initialMonth - 1)
    Napier.d { "initPage = $initPage" }

    var selectedDay by remember { mutableStateOf<CalendarDayModel?>(null) }
    var isOpen by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(
        initialPage = initPage,
        pageCount = { totalPages }
    )
    LaunchedEffect(pagerState.currentPage) {
        val currentPage = pagerState.currentPage
        // page 기반으로 year와 month를 계산해야함
        initialYear = 1900 + (currentPage / 12)
        initialMonth = (currentPage % 12) + 1
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .background(CaramelTheme.color.background.primary)
    ) {
        CalendarYear(
            modifier = Modifier
                .padding(start = 20.dp)
                .height(52.dp)
                .clickable { isOpen = !isOpen },
            year = initialYear,
            month = initialMonth,
            isOpen = isOpen
        )
        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = isOpen,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier.zIndex(1f)
            ) {
                CalendarDatePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 20.dp)
                        .wrapContentSize(align = Alignment.Center)
                        .background(color = CaramelTheme.color.dim.primary),
                    currentYear = initialYear,
                    currentMonth = initialMonth,
                    dismiss = { cYear, cMonth ->
                        isOpen = false
                        initialYear = cYear
                        initialMonth = cMonth
                        coroutineScope.launch {
                            pagerState.animateScrollToPage((initialYear - 1900) * 12 + (initialMonth - 1))
                        }
                    }
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.zIndex(0f)
            ) {
                CaramelCalendar(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    calendarModel = createSampleCalendarModel(
                        initialYear, initialMonth
                    ),
                    onDateClick = { dayModel ->
                        selectedDay = dayModel
                        Napier.d { "Selected day: $selectedDay" }
                    }
                )
            }
        }
    }
}