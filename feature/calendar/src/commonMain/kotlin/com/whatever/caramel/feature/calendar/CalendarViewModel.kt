package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.calendar.GetHolidaysUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetTodosGroupByStartDateUseCase
import com.whatever.caramel.core.domain.vo.calendar.Calendar
import com.whatever.caramel.core.domain.vo.calendar.HolidayList
import com.whatever.caramel.core.domain.vo.calendar.TodoList
import com.whatever.caramel.core.util.DateFormatter
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import io.github.aakira.napier.Napier
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
            pickerYear = currentDate.year,
            pickerMonth = currentDate.month.number,
            currentDateList = createCurrentDateList(
                year = currentDate.year,
                month = currentDate.month
            ),
            pageIndex = calcPageIndex(currentDate.year, currentDate.month)
        )
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            CalendarIntent.SwipeLeftCalendar -> decrementMonth()
            CalendarIntent.SwipeRightCalendar -> incrementMonth()
            is CalendarIntent.ToggleDatePicker -> toggleCalendarDatePicker()
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
            is CalendarIntent.ClickTodoItemInCalendar -> TODO()
            is CalendarIntent.UpdatePageIndex -> updatePageIndex(intent.index)
            is CalendarIntent.UpdateSelectPickerMonth -> updateSelectPickerMonth(intent.month)
            is CalendarIntent.UpdateSelectPickerYear -> updateSelectPickerYear(intent.year)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        Napier.e { "exception : $throwable" }
    }

    private fun updateSelectPickerYear(year: Int) {
        reduce {
            copy(
                pickerYear = year
            )
        }
    }

    private fun updateSelectPickerMonth(monthNumber: Int) {
        reduce {
            copy(
                pickerMonth = monthNumber
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
                pageIndex = pageIndex
            )
        }
        getSchedules(dateChange = true)
    }

    private fun clickTodoUrl(url: String?) {
        if (url == null) return
        postSideEffect(CalendarSideEffect.OpenWebView(url))
    }

    private fun clickCalendarCell(selectedDate: LocalDate) {
        reduce {
            val newSchedule = currentState.schedules.toMutableList()
            // 이전 선택 스케츌에 todo도 없고 휴일도 없다면 리스트에서 제거
            newSchedule.find { it.date == currentState.selectedDate }?.let {
                if (it.holidays.isEmpty() && it.todos.isEmpty()) {
                    newSchedule.remove(it)
                }
            }
            // 새로 선택된 날짜에 스케쥴이 없으면 빈 스케쥴 추가
            if (!newSchedule.any { it.date == selectedDate }) {
                newSchedule.add(DaySchedule(date = selectedDate))
            }

            copy(
                bottomSheetState = BottomSheetState.EXPANDED,
                selectedDate = selectedDate,
                schedules = newSchedule.sortedBy { it.date }
            )
        }
    }

    private fun getSchedules(dateChange: Boolean = false) {
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
                copy(
                    schedules = createDaySchedules(todoList = todos, holidayList = holidays)
                )
            }
            // 날짜가 변경됐다면 실행
            clickCalendarCell(
                if (dateChange) {
                    LocalDate(year = year, month = currentState.month, dayOfMonth = 1)
                } else {
                    currentState.today
                }
            )
        }
    }

    private fun toggleCalendarBottomSheet(bottomSheetState: BottomSheetState) {
        reduce {
            copy(
                bottomSheetState = bottomSheetState
            )
        }
    }

    private fun toggleCalendarDatePicker() {
        reduce {
            if (currentState.isShowDatePicker) {
                val pickerYear = currentState.pickerYear
                val pickerMonth = Month.entries[currentState.pickerMonth - 1]

                copy(
                    year = pickerYear,
                    month = pickerMonth,
                    isShowDatePicker = false,
                    pageIndex = calcPageIndex(pickerYear, pickerMonth),
                    currentDateList = createCurrentDateList(year = pickerYear, month = pickerMonth)
                )
            } else {
                copy(
                    isShowDatePicker = true
                )
            }
        }
        if(!currentState.isShowDatePicker){
            getSchedules(dateChange = true)
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

    private fun createDaySchedules(
        todoList: List<TodoList>,
        holidayList: List<HolidayList>
    ): List<DaySchedule> {
        val scheduleMap = mutableMapOf<LocalDate, DaySchedule>()

        todoList.forEach { list ->
            val date = list.date
            val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
            scheduleMap[date] = existingSchedule.copy(todos = list.todos)
        }

        holidayList.forEach { list ->
            val date = list.date
            val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
            scheduleMap[date] = existingSchedule.copy(holidays = list.holidays)
        }

        if (!scheduleMap.containsKey(currentState.selectedDate)) {
            scheduleMap[currentState.selectedDate] = DaySchedule(date = currentState.selectedDate)
        }
        return scheduleMap.values.sortedBy { it.date }
    }

    private fun calcPageIndex(year: Int, month: Month): Int {
        val index = Calendar.YEAR_RANGE.indexOf(year)
        return index * 12 + (month.number - 1)
    }
}