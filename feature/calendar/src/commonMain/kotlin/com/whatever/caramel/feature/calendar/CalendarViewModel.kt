package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.calendar.GetHolidaysUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetTodosGroupByStartDateUseCase
import com.whatever.caramel.core.domain.vo.calendar.Calendar
import com.whatever.caramel.core.domain.vo.calendar.HolidaysOnDate
import com.whatever.caramel.core.domain.vo.calendar.TodoOnDate
import com.whatever.caramel.core.util.DateFormatter
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number

class CalendarViewModel(
    private val getTodosGroupByStartDateUseCase: GetTodosGroupByStartDateUseCase,
    private val getHolidaysUseCase: GetHolidaysUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalendarState, CalendarSideEffect, CalendarIntent>(savedStateHandle) {

    init {
        getSchedules()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): CalendarState {
        val currentDate = DateUtil.today()
        return CalendarState(
            year = currentDate.year,
            month = currentDate.month,
            currentDateList = createCurrentDateList(
                year = currentDate.year,
                month = currentDate.month
            ),
            pageIndex = calcPageIndex(currentDate.year, currentDate.month)
        )
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.ClickDatePicker -> showCalendarDatePicker()
            is CalendarIntent.ToggleCalendarBottomSheet -> toggleCalendarBottomSheet(intent.sheetState)
            is CalendarIntent.ClickAddScheduleButton -> postSideEffect(
                CalendarSideEffect.NavigateToAddSchedule(
                    intent.date
                )
            )

            is CalendarIntent.ClickTodoItemInBottomSheet -> postSideEffect(
                CalendarSideEffect.NavigateToTodoDetail(
                    intent.todoId
                )
            )

            is CalendarIntent.ClickTodoUrl -> clickTodoUrl(intent.url)
            is CalendarIntent.ClickCalendarCell -> clickCalendarCell(intent.selectedDate)
            is CalendarIntent.ClickTodoItemInCalendar -> postSideEffect(
                CalendarSideEffect.NavigateToTodoDetail(
                    intent.todoId
                )
            )

            is CalendarIntent.UpdatePageIndex -> updatePageIndex(intent.index)
            is CalendarIntent.UpdateSelectPickerMonth -> updateSelectPickerMonth(intent.month)
            is CalendarIntent.UpdateSelectPickerYear -> updateSelectPickerYear(intent.year)
            CalendarIntent.ClickOutSideBottomSheet -> clickOutSideBottomSheet()
            CalendarIntent.ClickDatePickerOutSide -> dismissCalendarDatePicker()
        }
    }

    private fun clickOutSideBottomSheet() {
        reduce {
            copy(
                bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED
            )
        }
    }

    private fun updateSelectPickerYear(year: Int) {
        reduce {
            copy(
                pickerDate = pickerDate.copy(year = year)
            )
        }
    }

    private fun updateSelectPickerMonth(monthNumber: Int) {
        reduce {
            copy(
                pickerDate = pickerDate.copy(month = monthNumber)
            )
        }
    }

    private fun updatePageIndex(pageIndex: Int) {
        if (pageIndex == currentState.pageIndex) return

        val year = (pageIndex / 12) + 1900
        val monthNumber = pageIndex % 12
        reduce {
            copy(
                year = year,
                month = Month.entries[monthNumber],
                pageIndex = pageIndex,
            )
        }
        getSchedules(initialize = true)
    }

    private fun clickTodoUrl(url: String?) {
        if (url == null) return
        postSideEffect(CalendarSideEffect.OpenWebView(url))
    }

    private fun clickCalendarCell(newSelectedDate: LocalDate) {
        reduce {
            val newSchedule = currentState.schedules.toMutableList()
            // 이전에 선택된 날짜에 스케쥴이 존재하지 않는 경우 리스트에서 삭제
            newSchedule.find { it.date == currentState.selectedDate }?.let {
                if (it.holidays.isEmpty() && it.todos.isEmpty()) {
                    newSchedule.remove(it)
                }
            }
            // 새로 선택된 날짜에 스케쥴이 없으면 빈 스케쥴 추가
            if (!newSchedule.any { it.date == newSelectedDate }) {
                newSchedule.add(DaySchedule(date = newSelectedDate))
            }

            copy(
                bottomSheetState = BottomSheetState.EXPANDED,
                selectedDate = newSelectedDate,
                schedules = newSchedule.sortedBy { it.date }
            )
        }
    }

    private fun getSchedules(initialize: Boolean = false) {
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
            val todos = getTodosGroupByStartDateUseCase(
                startDate = firstDayOfMonth,
                endDate = lastDayOfMonth,
                userTimezone = TimeZone.currentSystemDefault().toString()
            )
            val holidays = getHolidaysUseCase(year = year, monthNumber = monthNumber)
            reduce {
                val updatedSelectedDate = if (initialize) {
                    LocalDate(year = year, month = currentState.month, dayOfMonth = 1)
                } else {
                    currentState.today
                }
                copy(
                    selectedDate = updatedSelectedDate,
                    schedules = createDaySchedules(
                        todoOnDate = todos,
                        holidaysOnDate = holidays,
                        updatedSelectedDate = updatedSelectedDate
                    )
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

    private fun showCalendarDatePicker() {
        reduce {
            copy(
                isShowDatePicker = true,
                pickerDate = pickerDate.copy(year = year, month = month.number)
            )
        }
    }

    private fun dismissCalendarDatePicker() {
        val pickerYear = currentState.pickerDate.year
        val pickerMonth = Month.entries[currentState.pickerDate.month - 1]
        val needUpdate = pickerYear != currentState.year || pickerMonth != currentState.month

        reduce {
            copy(
                isShowDatePicker = false,
                bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED
            )
        }

        if (needUpdate) {
            getSchedules(initialize = true)
            reduce {
                copy(
                    year = pickerYear,
                    month = pickerMonth,
                    pageIndex = calcPageIndex(pickerYear, pickerMonth),
                    currentDateList = createCurrentDateList(
                        year = pickerYear,
                        month = pickerMonth
                    )
                )
            }
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

    private fun createDaySchedules(
        todoOnDate: List<TodoOnDate>,
        holidaysOnDate: List<HolidaysOnDate>,
        updatedSelectedDate: LocalDate
    ): List<DaySchedule> {
        val scheduleMap = mutableMapOf<LocalDate, DaySchedule>()

        todoOnDate.forEach { list ->
            val date = list.date
            val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
            scheduleMap[date] = existingSchedule.copy(todos = list.todos)
        }

        holidaysOnDate.forEach { list ->
            val date = list.date
            val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
            scheduleMap[date] = existingSchedule.copy(holidays = list.holidays)
        }

        if (!scheduleMap.containsKey(updatedSelectedDate)) {
            scheduleMap[updatedSelectedDate] = DaySchedule(date = updatedSelectedDate)
        }

        return scheduleMap.values.sortedBy { it.date }
    }

    private fun calcPageIndex(year: Int, month: Month): Int {
        val index = Calendar.YEAR_RANGE.indexOf(year)
        return index * 12 + (month.number - 1)
    }
}