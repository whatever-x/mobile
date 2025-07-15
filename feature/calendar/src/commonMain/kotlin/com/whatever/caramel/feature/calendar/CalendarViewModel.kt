package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.calendar.GetHolidaysUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetTodosGroupByStartDateUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetAnniversariesUseCase
import com.whatever.caramel.core.domain.vo.calendar.AnniversariesOnDate
import com.whatever.caramel.core.domain.vo.calendar.Calendar
import com.whatever.caramel.core.domain.vo.calendar.HolidaysOnDate
import com.whatever.caramel.core.domain.vo.calendar.TodosOnDate
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.util.DateFormatter
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.number

class CalendarViewModel(
    private val getTodosGroupByStartDateUseCase: GetTodosGroupByStartDateUseCase,
    private val getHolidaysUseCase: GetHolidaysUseCase,
    private val getAnniversariesUseCase: GetAnniversariesUseCase,
    crashlytics: CaramelCrashlytics,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CalendarState, CalendarSideEffect, CalendarIntent>(
    savedStateHandle,
    crashlytics,
) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): CalendarState {
        val currentDate = DateUtil.today()
        return CalendarState(
            year = currentDate.year,
            month = currentDate.month,
            currentDateList =
                createCurrentDateList(
                    year = currentDate.year,
                    month = currentDate.month,
                ),
            pageIndex = calcPageIndex(currentDate.year, currentDate.month),
        )
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        CalendarSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )

                ErrorUiType.DIALOG ->
                    postSideEffect(
                        CalendarSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                CalendarSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
        }
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.ClickDatePicker -> showCalendarDatePicker()
            is CalendarIntent.UpdateCalendarBottomSheet -> updateCalendarBottomSheet(intent.sheetState)
            is CalendarIntent.ClickAddScheduleButton -> navigateToAddSchedule(intent.date)
            is CalendarIntent.ClickTodoItemInBottomSheet ->
                postSideEffect(
                    CalendarSideEffect.NavigateToTodoDetail(
                        id = intent.todoId,
                        contentType = ContentType.CALENDAR,
                    ),
                )

            is CalendarIntent.ClickTodoUrl -> clickTodoUrl(intent.url)
            is CalendarIntent.ClickCalendarCell -> clickCalendarCell(intent.selectedDate)
            is CalendarIntent.ClickTodoItemInCalendar ->
                postSideEffect(
                    CalendarSideEffect.NavigateToTodoDetail(
                        id = intent.todoId,
                        contentType = ContentType.CALENDAR,
                    ),
                )

            is CalendarIntent.UpdatePageIndex -> updatePageIndex(intent.index)
            is CalendarIntent.UpdateSelectPickerMonth -> updateSelectPickerMonth(intent.month)
            is CalendarIntent.UpdateSelectPickerYear -> updateSelectPickerYear(intent.year)
            CalendarIntent.ClickOutSideBottomSheet -> clickOutSideBottomSheet()
            CalendarIntent.ClickDatePickerOutSide -> dismissCalendarDatePicker()
            CalendarIntent.RefreshCalendar -> refreshCalendar()
            CalendarIntent.Initialize -> initialize()
            is CalendarIntent.DraggingCalendarBottomSheet -> draggingBottomSheetHandle(intent.isDragging)
        }
    }

    private fun initialize() {
        reduce { copy(cachedYearSchedules = emptyMap()) }
        getYearSchedules(
            year = currentState.year,
            initialize = currentState.yearSchedule.isEmpty(),
            isRefresh = true,
        )
    }

    private fun draggingBottomSheetHandle(isDragging: Boolean) {
        reduce {
            copy(
                isBottomSheetDragging = isDragging,
            )
        }
    }

    private fun navigateToAddSchedule(date: LocalDate) {
        reduce { copy(bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED) }
        postSideEffect(
            CalendarSideEffect.NavigateToAddSchedule(
                date.atTime(hour = 0, minute = 0).toString(),
            ),
        )
    }

    private fun refreshCalendar() {
        reduce {
            copy(isRefreshing = true, cachedYearSchedules = emptyMap())
        }
        getYearSchedules(
            year = currentState.year,
            isRefresh = true,
        )
    }

    private fun clickOutSideBottomSheet() {
        reduce {
            val newBottomSheetState =
                if (currentState.isBottomSheetDragging) currentState.bottomSheetState else BottomSheetState.PARTIALLY_EXPANDED
            copy(
                bottomSheetState = newBottomSheetState,
            )
        }
    }

    private fun updateSelectPickerYear(year: Int) {
        reduce {
            copy(
                pickerDate = pickerDate.copy(year = year),
            )
        }
    }

    private fun updateSelectPickerMonth(monthNumber: Int) {
        reduce {
            copy(
                pickerDate = pickerDate.copy(month = monthNumber),
            )
        }
    }

    private fun updatePageIndex(pageIndex: Int) {
        if (pageIndex == currentState.pageIndex) return

        val (year, month) = getYearAndMonthFromPageIndex(index = pageIndex)
        getYearSchedules(year = year)
        reduce {
            copy(
                year = year,
                month = month,
                pageIndex = pageIndex,
            )
        }
    }

    private fun clickTodoUrl(url: String?) {
        if (url == null) return
        postSideEffect(CalendarSideEffect.OpenWebView(url))
    }

    private fun clickCalendarCell(newSelectedDate: LocalDate) {
        reduce {
            val newSchedule = currentState.yearSchedule.toMutableList()
            newSchedule.find { it.date == currentState.selectedDate }?.let {
                if (it.holidays.isEmpty() && it.todos.isEmpty() && it.anniversaries.isEmpty()) {
                    newSchedule.remove(it)
                }
            }
            if (!newSchedule.any { it.date == newSelectedDate }) {
                newSchedule.add(DaySchedule(date = newSelectedDate))
            }
            copy(
                bottomSheetState = BottomSheetState.EXPANDED,
                selectedDate = newSelectedDate,
                yearSchedule = newSchedule.sortedBy { it.date },
            )
        }
    }

    private fun applyCachedSchedulesIfExists(updateSelectedDate: LocalDate): Boolean {
        if (currentState.cachedYearSchedules.contains(updateSelectedDate.year)) {
            val filteredCachedSchedule = (currentState.cachedYearSchedules[updateSelectedDate.year]
                ?: emptyList()).toMutableList()
            if (filteredCachedSchedule.find { daySchedule -> daySchedule.date == updateSelectedDate } == null) {
                filteredCachedSchedule.add(0, DaySchedule(date = updateSelectedDate))
            }
            reduce {
                copy(
                    isRefreshing = false,
                    selectedDate = updateSelectedDate,
                    yearSchedule = filteredCachedSchedule,
                )
            }
            return true
        }
        return false
    }

    private fun getYearSchedules(
        year: Int,
        initialize: Boolean = false,
        isRefresh: Boolean = false,
    ) {
        launch {
            val updateSelectedDate = when {
                initialize -> currentState.today
                isRefresh -> currentState.selectedDate
                else -> LocalDate(year = year, month = currentState.month, dayOfMonth = 1)
            }
            if (applyCachedSchedulesIfExists(updateSelectedDate)) return@launch
            val firstDayOfMonth = DateFormatter.createDateString(
                year = year,
                month = 1,
                day = 1,
            )
            val lastDay = DateUtil.getLastDayOfMonth(year = year, month = 12)
            val lastDayOfMonth = DateFormatter.createDateString(
                year = year,
                month = 12,
                day = lastDay,
            )
            val todosDeferred = async {
                getTodosGroupByStartDateUseCase(
                    startDate = firstDayOfMonth,
                    endDate = lastDayOfMonth,
                    userTimezone = TimeZone.currentSystemDefault().toString()
                )
            }
            val anniversariesDeferred = async {
                getAnniversariesUseCase(
                    startDate = firstDayOfMonth,
                    endDate = lastDayOfMonth
                )
            }
            val holidaysDeferred = async { getHolidaysUseCase(year) }

            val todos = todosDeferred.await()
            val anniversaries = anniversariesDeferred.await()
            val holidays = holidaysDeferred.await()

            var yearSchedule = createYearSchedules(
                todosOnDate = todos,
                holidaysOnDate = holidays,
                anniversariesOnDate = anniversaries,
            )

            if (updateSelectedDate !in yearSchedule.map { it.date }) {
                yearSchedule = (yearSchedule + DaySchedule(date = updateSelectedDate)).sortedBy { it.date }
            }
            val updatedCache = currentState.cachedYearSchedules
                .toMutableMap()
                .apply {
                    if (size >= 3) remove(keys.first())
                    put(year, yearSchedule)
                }

            reduce {
                copy(
                    isRefreshing = false,
                    selectedDate = updateSelectedDate,
                    yearSchedule = yearSchedule,
                    cachedYearSchedules = updatedCache,
                )
            }
        }
    }

    private fun updateCalendarBottomSheet(bottomSheetState: BottomSheetState) {
        reduce { copy(bottomSheetState = bottomSheetState, isBottomSheetDragging = false) }
    }

    private fun showCalendarDatePicker() {
        launch {
            if (currentState.isBottomSheetDragging) return@launch
            clickOutSideBottomSheet()
            reduce {
                copy(
                    isShowDatePicker = true,
                    pickerDate = pickerDate.copy(year = year, month = month.number),
                )
            }
        }
    }

    private fun dismissCalendarDatePicker() {
        val pickerYear = currentState.pickerDate.year
        val pickerMonth = Month.entries[currentState.pickerDate.month - 1]
        val isSame = pickerYear == currentState.year && pickerMonth == currentState.month
        getYearSchedules(
            year = pickerYear,
            isRefresh = isSame,
        )

        reduce {
            copy(
                year = pickerYear,
                month = pickerMonth,
                pageIndex = calcPageIndex(pickerYear, pickerMonth),
                currentDateList =
                    createCurrentDateList(
                        year = pickerYear,
                        month = pickerMonth,
                    ),
                isShowDatePicker = false,
                bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
            )
        }
    }

    private fun createCurrentDateList(
        year: Int,
        month: Month,
    ): List<LocalDate> {
        val dateList = mutableListOf<LocalDate>()
        val lastDay = DateUtil.getLastDayOfMonth(year = year, month = month.number)
        for (day in 1..lastDay) {
            dateList.add(LocalDate(year = year, month = month, dayOfMonth = day))
        }
        return dateList.toList()
    }

    private fun createYearSchedules(
        todosOnDate: List<TodosOnDate>,
        holidaysOnDate: List<HolidaysOnDate>,
        anniversariesOnDate: List<AnniversariesOnDate>,
    ): List<DaySchedule> {
        val scheduleMap = mutableMapOf<LocalDate, DaySchedule>()
        todosOnDate
            .forEach { todo ->
                val date = todo.date
                val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
                scheduleMap[date] =
                    existingSchedule.copy(todos = existingSchedule.todos + todo.todos)
            }

        anniversariesOnDate
            .forEach { anniversary ->
                val date = anniversary.date
                val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
                scheduleMap[date] =
                    existingSchedule.copy(anniversaries = existingSchedule.anniversaries + anniversary.anniversaries)
            }

        holidaysOnDate
            .forEach { holiday ->
                val date = holiday.date
                val existingSchedule = scheduleMap[date] ?: DaySchedule(date = date)
                scheduleMap[date] =
                    existingSchedule.copy(holidays = existingSchedule.holidays + holiday.holidays)
            }

        return scheduleMap.values.sortedBy { it.date }
    }

    private fun calcPageIndex(
        year: Int,
        month: Month,
    ): Int {
        val index = Calendar.YEAR_RANGE.indexOf(year)
        return index * 12 + (month.number - 1)
    }
}
