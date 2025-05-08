package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.calendar.GetSchedulesUseCaseGroupByStartDate
import com.whatever.caramel.core.util.DateFormatter
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

class CalendarViewModel(
    private val getSchedulesUseCaseGroupByStartDate: GetSchedulesUseCaseGroupByStartDate,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarState, CalendarSideEffect, CalendarIntent>(savedStateHandle) {

    init {
        getSchedules()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): CalendarState {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return CalendarState(
            year = currentDate.year,
            month = currentDate.month,
            currentDateList = createCurrentDateList(
                year = currentDate.year,
                month = currentDate.month
            )
        )
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            CalendarIntent.SwipeLeftCalendar -> decrementMonth()
            CalendarIntent.SwipeRightCalendar -> incrementMonth()
            CalendarIntent.OpenCalendarDatePicker -> openCalendarDatePicker()
            is CalendarIntent.ToggleCalendarBottomSheet -> toggleCalendarBottomSheet(intent.sheetState)
            is CalendarIntent.ClickAddScheduleButton -> postSideEffect(
                CalendarSideEffect.NavigateToAddSchedule(
                    intent.date
                )
            )

            is CalendarIntent.ClickTodoItem -> postSideEffect(
                CalendarSideEffect.NavigateToTodoDetail(
                    intent.id
                )
            )

            is CalendarIntent.ClickTodoUrl -> clickTodoUrl(intent.url)
            is CalendarIntent.DismissCalendarDatePicker -> updateCalendarDate(
                year = intent.year,
                monthNumber = intent.monthNumber
            )
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        Napier.e { "exception : $throwable" }
    }

    private fun clickTodoUrl(url: String?) {
        if (url == null) return
        postSideEffect(CalendarSideEffect.OpenWebView(url))
    }

    private fun updateCalendarDate(year: Int, monthNumber: Int) {
        val month = Month.entries[monthNumber - 1]
        reduce {
            copy(
                year = year,
                month = month,
                isShownDateSelectDropDown = false,
                currentDateList = createCurrentDateList(year = year, month = month)
            )
        }
        getSchedules()
    }

    private fun getSchedules() {
        launch {
            val year = currentState.year
            val monthNumber = currentState.month.number
            val firstDayOfMonth = DateFormatter.createDateString(
                year = year,
                month = monthNumber,
                day = 1
            )
            val lastDay = DateUtil.getLastDayOfMonth(year = year, month = monthNumber)
            val lastDayOfMonth = DateFormatter.createDateString(
                year = year,
                month = monthNumber,
                day = lastDay
            )
            val schedules = getSchedulesUseCaseGroupByStartDate(
                startDate = firstDayOfMonth,
                endDate = lastDayOfMonth,
                userTimezone = TimeZone.currentSystemDefault().toString()
            )
            reduce {
                copy(
                    schedules = schedules
                )
            }
        }
    }

    private fun toggleCalendarBottomSheet(bottomSheetState: BottomSheetState) {
        reduce {
            copy(
                bottomSheetState = bottomSheetState
            )
        }
    }

    private fun openCalendarDatePicker() {
        reduce {
            copy(
                isShownDateSelectDropDown = true
            )
        }
    }

    private fun incrementMonth() {
        reduce {
            val newMonth =
                if (currentState.month == Month.DECEMBER) Month.JANUARY else Month.entries[currentState.month.ordinal + 1]
            val newYear =
                if (currentState.month == Month.DECEMBER) currentState.year + 1 else currentState.year
            copy(
                year = newYear,
                month = newMonth,
                currentDateList = createCurrentDateList(
                    year = newYear,
                    month = newMonth
                )
            )
        }
    }

    private fun decrementMonth() {
        reduce {
            val newMonth =
                if (currentState.month == Month.JANUARY) Month.DECEMBER else Month.entries[currentState.month.ordinal - 1]
            val newYear =
                if (currentState.month == Month.JANUARY) currentState.year - 1 else currentState.year
            copy(
                year = newYear,
                month = newMonth,
                currentDateList = createCurrentDateList(
                    year = newYear,
                    month = newMonth
                )
            )
        }
    }

    private fun createCurrentDateList(
        year: Int,
        month: Month
    ): List<LocalDate> {
        val dateList = mutableListOf<LocalDate>()
        val lastDay = DateUtil.getLastDayOfMonth(year = year, month = month.number)
        for (day in 1..lastDay) {
            dateList.add(LocalDate(year = year, month = month, dayOfMonth = day))
        }
        return dateList.toList()
    }
}