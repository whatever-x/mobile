package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.policy.CalendarPolicy
import com.whatever.caramel.core.domain.usecase.calendar.GetAnniversariesInPeriodUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetHolidayOfYearUseCase
import com.whatever.caramel.core.domain.usecase.schedule.GetScheduleInPeriodUseCase
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.util.DateFormatter
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mapper.toScheduleCell
import com.whatever.caramel.feature.calendar.mapper.toScheduleUiModel
import com.whatever.caramel.feature.calendar.model.CalendarBottomSheet
import com.whatever.caramel.feature.calendar.model.CalendarCell
import com.whatever.caramel.feature.calendar.model.CalendarUiModel
import com.whatever.caramel.feature.calendar.model.CalendarUiResult
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.util.appOrdianl
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import com.whatever.caramel.feature.calendar.util.weekOfMonth
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.collections.set
import kotlin.math.min
import kotlin.time.Duration.Companion.days

class CalendarViewModel(
    private val getScheduleInPeriodUseCase: GetScheduleInPeriodUseCase,
    private val getHolidayOfYearUseCase: GetHolidayOfYearUseCase,
    private val getAnniversariesInPeriodUseCase: GetAnniversariesInPeriodUseCase,
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
        Napier.e { "error : $throwable" }
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
            is CalendarIntent.ClickScheduleItemInBottomSheet ->
                postSideEffect(
                    CalendarSideEffect.NavigateToScheduleDetail(
                        id = intent.scheduleId,
                        contentType = ContentType.CALENDAR,
                    ),
                )

            is CalendarIntent.ClickScheduleUrl -> clickScheduleUrl(intent.url)
            is CalendarIntent.ClickCalendarCell -> clickCalendarCell(intent.selectedDate)
            is CalendarIntent.ClickScheduleItemInCalendar ->
                postSideEffect(
                    CalendarSideEffect.NavigateToScheduleDetail(
                        id = intent.schedule,
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
            is CalendarIntent.PressCalendarBottomSheetHandle -> pressBottomSheetHandle()
        }
    }

    private fun pressBottomSheetHandle() {
        reduce {
            copy(
                isShowDatePicker = false,
            )
        }
    }

    private fun initialize() {
        reduce { copy(yearCacheList = emptyList()) }
        getYearSchedules(
            year = currentState.year,
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
            copy(isRefreshing = true, yearCacheList = emptyList())
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

    private fun clickScheduleUrl(url: String?) {
        if (url == null) return
        postSideEffect(CalendarSideEffect.OpenWebView(url))
    }

    private fun clickCalendarCell(newSelectedDate: LocalDate) {
        reduce {
            val bottomSheetList = currentState.calendarBottomSheetList.toMutableList()
            // 선택된 날짜 가져오기
            bottomSheetList.find { it.date == currentState.selectedDate }?.let { bottomSheetInfo ->
                // TODO : mainText가 빈값일때 해당 일정 삭제 로직 구현 필요
                bottomSheetInfo.scheduleList
            }


            if (!bottomSheetList.any { it.date == newSelectedDate }) {
                bottomSheetList.add(
                    CalendarBottomSheet(
                        date = newSelectedDate,
                        scheduleList = CalendarUiModel()
                    )
                )
            }
            copy(
                bottomSheetState = BottomSheetState.EXPANDED,
                selectedDate = newSelectedDate,
                calendarBottomSheetList = bottomSheetList.sortedBy { it.date },
            )
        }
    }

    private fun applyCachedSchedulesIfExists(updateSelectedDate: LocalDate): Boolean {
        if (currentState.cachedYearScheduleList.contains(updateSelectedDate.year)) {
            val filteredCachedSchedule =
                (
                        currentState.cachedYearScheduleList[updateSelectedDate.year]
                            ?: emptyList()
                        ).toMutableList()

            if (filteredCachedSchedule.find { it.date == currentState.selectedDate } == null) {
                filteredCachedSchedule.add(DaySchedule(date = currentState.selectedDate))
            }
            reduce {
                copy(
                    isRefreshing = false,
                    selectedDate = updateSelectedDate,
                    calendarBottomSheetList = filteredCachedSchedule,
                )
            }
            return true
        }
        return false
    }

    private fun getYearSchedules(
        year: Int,
        isRefresh: Boolean = false,
    ) {
        launch {
            val updateSelectedDate =
                when {
                    currentState.isInitialized -> currentState.today
                    isRefresh -> currentState.selectedDate
                    else -> LocalDate(year = year, month = currentState.month, dayOfMonth = 1)
                }
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
            val scheduleListDeferred =
                async {
                    getScheduleInPeriodUseCase(
                        startDate = firstDayOfMonth,
                        endDate = lastDayOfMonth,
                    )
                }
            val anniversariesDeferred =
                async {
                    getAnniversariesInPeriodUseCase(
                        startDate = firstDayOfMonth,
                        endDate = lastDayOfMonth,
                    )
                }
            val holidaysDeferred = async { getHolidayOfYearUseCase(year) }
            val scheduleList = scheduleListDeferred.await()
            val anniversaries = anniversariesDeferred.await()
            val holidayList = holidaysDeferred.await()

            val test = createScheduleUiModel(
                scheduleList = scheduleList,
                holidayList = holidayList,
                anniversaryList = anniversaries,
            )

            var yearSchedule =
                createYearSchedules(
                    scheduleList = scheduleList,
                    holidayList = holidayList,
                    anniversaryList = anniversaries,
                )
            if (yearSchedule.find { it.date == updateSelectedDate } == null) {
                yearSchedule =
                    (yearSchedule + DaySchedule(date = updateSelectedDate)).sortedBy { it.date }
            }
            val updatedCache =
                currentState.cachedYearScheduleList
                    .toMutableMap()
                    .apply {
                        if (size >= 3) remove(keys.first())
                        put(year, yearSchedule)
                    }

            reduce {
                copy(
                    isInitialized = false,
                    isRefreshing = false,
                    selectedDate = updateSelectedDate,
                    calendarBottomSheetList = yearSchedule,
                    cachedYearScheduleList = updatedCache,
                    calendarCellList = test,
                )
            }
        }
    }

    private fun updateCalendarBottomSheet(bottomSheetState: BottomSheetState) {
        reduce { copy(bottomSheetState = bottomSheetState, isBottomSheetDragging = false) }
    }

    private fun showCalendarDatePicker() {
        reduce {
            copy(
                isShowDatePicker = true,
                bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
                pickerDate = pickerDate.copy(year = year, month = month.number),
            )
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

    private fun createScheduleUiModel(
        scheduleList: List<Schedule>,
        holidayList: List<Holiday>,
        anniversaryList: List<Anniversary>,
    ): CalendarUiResult {
        val calendarCellMap = mutableMapOf<CalendarCell, List<CalendarCell.CellUiModel>>()
        val calendarBottomSheetMap = mutableMapOf<LocalDate, CalendarBottomSheet>()

        scheduleList.forEach { schedule ->
            with(schedule.dateTimeInfo) {
                val startDateInstant = startDateTime.toInstant(TimeZone.of(startTimezone))
                val endDateInstant = endDateTime.toInstant(TimeZone.of(endTimezone))
                generateSequence(startDateInstant) { it.plus(1.days) }
                    .takeWhile { it <= endDateInstant }
                    .forEach { current ->
                        val currentLocalDateTime =
                            current.toLocalDateTime(TimeZone.of(startTimezone))
                        // 바텀시트
                        val bottomSheetKey = currentLocalDateTime.date
                        val existBottomSheetList = calendarBottomSheetMap[bottomSheetKey]
                            ?: CalendarBottomSheet(date = bottomSheetKey)
                        calendarBottomSheetMap[bottomSheetKey] =
                            existBottomSheetList.copy(scheduleList = existBottomSheetList.scheduleList + schedule.toScheduleUiModel())
                        // 캘린더 셀
                        val key = CalendarCell(
                            year = currentLocalDateTime.year,
                            month = currentLocalDateTime.month,
                            weekendIndex = currentLocalDateTime.weekOfMonth()
                        )
                        val existList = calendarCellMap[key].orEmpty()
                        val existModel = existList.find { it.base.id == schedule.id }
                        val rowStartIndex = when {
                            (startDateTime.month != currentLocalDateTime.month) &&
                                    currentLocalDateTime.weekOfMonth() == 0 -> {
                                currentLocalDateTime.dayOfWeek.appOrdianl
                            }

                            startDateTime.weekOfMonth() != currentLocalDateTime.weekOfMonth() -> 0
                            else -> startDateTime.dayOfWeek.appOrdianl
                        }
                        val updated = existModel?.copy(
                            rowStartIndex = min(rowStartIndex, existModel.rowStartIndex),
                            rowEndIndex = currentLocalDateTime.dayOfWeek.appOrdianl
                        ) ?: schedule.toScheduleCell().copy(
                            rowStartIndex = rowStartIndex,
                            rowEndIndex = currentLocalDateTime.dayOfWeek.appOrdianl
                        )
                        calendarCellMap[key] = if (existModel == null) {
                            existList + updated
                        } else {
                            existList.map { if (it.base.id == updated.base.id) updated else it }
                        }
                    }
            }
        }
        holidayList.forEach { holiday ->
            val date = holiday.date
            // 바텀시트
            val existBottomSheet = calendarBottomSheetMap[date] ?: CalendarBottomSheet(date = date)
            calendarBottomSheetMap[date] = existBottomSheet.copy(
                scheduleList = existBottomSheet.scheduleList + holiday.toScheduleUiModel()
            )
            // 스케쥴 셀
            val keyValue = CalendarCell(date.year, date.month, date.weekOfMonth())
            val existScheduleUiList = calendarCellMap[keyValue] ?: emptyList()
            if (existScheduleUiList.isEmpty()) {
                calendarCellMap.put(keyValue, listOf(holiday.toScheduleCell()))
            } else {
                calendarCellMap[keyValue] = existScheduleUiList + holiday.toScheduleCell()
            }
        }
        anniversaryList.forEach { anniversary ->
            val date = anniversary.date
            // 바텀시트
            val existBottomSheet = calendarBottomSheetMap[date] ?: CalendarBottomSheet(date = date)
            calendarBottomSheetMap[date] = existBottomSheet.copy(
                scheduleList = existBottomSheet.scheduleList + anniversary.toScheduleUiModel()
            )
            // 스케쥴 셀
            val keyValue = CalendarCell(date.year, date.month, date.weekOfMonth())
            val existScheduleUiList = calendarCellMap[keyValue] ?: emptyList()
            if (existScheduleUiList.isEmpty()) {
                calendarCellMap.put(keyValue, listOf(anniversary.toScheduleCell()))
            } else {
                calendarCellMap[keyValue] = existScheduleUiList + anniversary.toScheduleCell()
            }
        }
        val returnList = mutableListOf<CalendarCell>()
        calendarCellMap.forEach { (key, value) ->
            returnList.add(key.copy(scheduleList = assignColumnIndex(value)))
        }
        return CalendarUiResult(
            cellList = returnList,
            bottomSheetList = calendarBottomSheetMap.values.sortedBy { it.date }
        )
    }

    fun assignColumnIndex(scheduleList: List<CalendarCell.CellUiModel>): List<CalendarCell.CellUiModel> {
        val sortedList = scheduleList.sortedWith(
            compareBy<CalendarCell.CellUiModel> { it.base.type.priority }
                .thenBy { schedule ->
                    if (schedule.base.type == CalendarUiModel.ScheduleType.MULTI_SCHEDULE) schedule.rowStartIndex else 0
                }
                .thenByDescending { schedule ->
                    if (schedule.base.type == CalendarUiModel.ScheduleType.MULTI_SCHEDULE) schedule.rowEndIndex - schedule.rowStartIndex else 0
                }
        )
        val rowColumns = mutableMapOf<Int, MutableSet<Int>>()
        val result = mutableListOf<CalendarCell.CellUiModel>()

        for (schedule in sortedList) {
            val start = schedule.rowStartIndex
            val end = schedule.rowEndIndex

            var column = 0
            while ((start..end).any { row -> rowColumns[row]?.contains(column) ?: false }) {
                column++
            }

            for (row in start..end) {
                val columns = rowColumns.getOrPut(row) { mutableSetOf() }
                columns.add(column)
            }
            result.add(schedule.copy(columnIndex = column))
        }
        return result
    }

    private fun calcPageIndex(
        year: Int,
        month: Month,
    ): Int {
        val yearRange = CalendarPolicy.MIN_YEAR..CalendarPolicy.MAX_YEAR
        val index = yearRange.indexOf(year)
        return index * 12 + (month.number - 1)
    }
}
