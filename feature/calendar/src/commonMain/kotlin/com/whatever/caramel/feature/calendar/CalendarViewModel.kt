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

        val year = (pageIndex / 12) + 1900
        val monthNumber = pageIndex % 12
        getYearSchedules(year = year)
        reduce {
            copy(
                year = year,
                month = Month.entries[monthNumber],
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
            // 이전에 선택된 날짜에 스케쥴이 존재하지 않는 경우 리스트에서 삭제
            newSchedule.find { it.date == currentState.selectedDate }?.let {
                if (it.holidays.isEmpty() && it.todos.isEmpty() && it.anniversaries.isEmpty()) {
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
                yearSchedule = newSchedule.sortedBy { it.date },
            )
        }
    }

    private fun applyCachedSchedulesIfExists(updateSelectedDate: LocalDate): Boolean {
        // 현재 년도에 적용된 캐싱 데이터가 존재하는가?
        if (currentState.cachedYearSchedules.contains(updateSelectedDate.year)) {
            // API 기준으로 캐싱된 값
            val filteredCachedSchedule =
                (
                    currentState.cachedYearSchedules[updateSelectedDate.year]
                        ?: emptyList()
                ).toMutableList()
            // 선택된 값이 존재하지 않는다면 추가로 넣어줘야한다.
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
            val updateSelectedDate =
                when {
                    initialize -> currentState.today
                    isRefresh -> currentState.selectedDate
                    else -> LocalDate(year = year, month = currentState.month, dayOfMonth = 1)
                }
            // 만약 캐싱 데이터가 존재하면 캐싱 데이터를 사용하고 밑의 로직은 사용되지 않는다.
            if (applyCachedSchedulesIfExists(updateSelectedDate)) return@launch

            val firstDayOfMonth =
                DateFormatter.createDateString(
                    year = year,
                    month = 1,
                    day = 1,
                )
            val lastDay = DateUtil.getLastDayOfMonth(year = year, month = 12)
            val lastDayOfMonth =
                DateFormatter.createDateString(
                    year = year,
                    month = 12,
                    day = lastDay,
                )
            val todos =
                getTodosGroupByStartDateUseCase(
                    startDate = firstDayOfMonth,
                    endDate = lastDayOfMonth,
                    userTimezone = TimeZone.currentSystemDefault().toString(),
                )
            val anniversaries =
                getAnniversariesUseCase(
                    startDate = firstDayOfMonth,
                    endDate = lastDayOfMonth,
                )
            val holidays = getHolidaysUseCase(year = year)
            // 저장되는 값
            val yearSchedule =
                createYearSchedules(
                    todosOnDate = todos,
                    holidaysOnDate = holidays,
                    anniversariesOnDate = anniversaries,
                )
            // 캐시 최대 허용량 넘어가는 경우
            if (currentState.cachedYearSchedules.size >= 3) {
                val currentCachedSchedule =
                    currentState.cachedYearSchedules.toMutableMap().apply {
                        remove(entries.first().key)
                    }
                reduce { copy(cachedYearSchedules = currentCachedSchedule) }
            }
            val yearScheduleByCached = mapOf(year to yearSchedule)
            // updateSelectedDate가 존재하는지 확인
            reduce {
                copy(
                    isRefreshing = false,
                    selectedDate = updateSelectedDate,
                    yearSchedule =
                        if (yearSchedule.find { daySchedule -> daySchedule.date == updateSelectedDate } == null) {
                            (yearSchedule + DaySchedule(date = updateSelectedDate)).sortedBy { it.date }
                        } else {
                            yearSchedule
                        },
                    cachedYearSchedules = cachedYearSchedules + yearScheduleByCached,
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
