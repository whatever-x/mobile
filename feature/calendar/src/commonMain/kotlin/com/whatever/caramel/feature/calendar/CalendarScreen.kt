package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.domain.model.calendar.CalendarDayModel
import com.whatever.caramel.core.domain.model.calendar.CalendarModel.Companion.createSampleCalendarModel
import com.whatever.caramel.feature.calendar.component.CalendarYear
import com.whatever.caramel.feature.calendar.component.CaramelCalendar
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit
) {
    // 현재 날짜로 캘린더 모델 생성
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val initialYear = currentDate.year
    val initialMonth = currentDate.monthNumber

    var calendarModel by remember {
        mutableStateOf(
            createSampleCalendarModel(initialYear, initialMonth)
        )
    }

    var selectedDay by remember { mutableStateOf<CalendarDayModel?>(null) }

    Column {
        // 년월 표시
        CalendarYear(
            modifier = Modifier
                .padding(start = 20.dp)
                .height(52.dp),
            year = calendarModel.year,
            month = calendarModel.month,
            onYearSelected = { _, _ -> }
        )
        

        CaramelCalendar(
            modifier = Modifier.padding(horizontal = 20.dp),
            calendarModel = calendarModel,
            onDateClick = { dayModel ->
                selectedDay = dayModel
            }
        )
    }
}