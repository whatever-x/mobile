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
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
import com.whatever.caramel.feature.calendar.util.getYearAndMonthFromPageIndex
import kotlinx.coroutines.async
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
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
        reduce { copy(cachedYearScheduleList = emptyMap()) }
        getYearSchedules(
            year = currentState.year,
            initialize = currentState.yearScheduleList.isEmpty(),
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
            copy(isRefreshing = true, cachedYearScheduleList = emptyMap())
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
            val newSchedule = currentState.yearScheduleList.toMutableList()
            newSchedule.find { it.date == currentState.selectedDate }?.let {
                if(it.holidayList.isEmpty() && it.anniversaryList.isEmpty() && it.scheduleList.isEmpty()) newSchedule.remove(it)
            }

            if (!newSchedule.any { it.date == newSelectedDate }) {
                newSchedule.add(DaySchedule(date = newSelectedDate))
            }
            copy(
                bottomSheetState = BottomSheetState.EXPANDED,
                selectedDate = newSelectedDate,
                yearScheduleList = newSchedule.sortedBy { it.date },
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
                    yearScheduleList = filteredCachedSchedule,
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

            var yearSchedule =
                createYearSchedules(
                    scheduleList = scheduleList,
                    holidayList = holidayList,
                    anniversaryList = anniversaries,
                )
            if (yearSchedule.find { it.date == updateSelectedDate } == null) {
                yearSchedule = (yearSchedule + DaySchedule(date = updateSelectedDate)).sortedBy { it.date }
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
                    isRefreshing = false,
                    selectedDate = updateSelectedDate,
                    yearScheduleList = yearSchedule,
                    cachedYearScheduleList = updatedCache,
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

    private fun createYearSchedules(
        scheduleList: List<Schedule>,
        holidayList: List<Holiday>,
        anniversaryList: List<Anniversary>,
    ): List<DaySchedule> {
        val totalSchedule = mutableMapOf<LocalDate, DaySchedule>()
        val timeZone = TimeZone.currentSystemDefault()

        scheduleList.forEach { schedule ->
            with(schedule.dateTimeInfo) {
                val startDateInstant = startDateTime.toInstant(TimeZone.of(startTimezone))
                val endDateInstant = endDateTime.toInstant(TimeZone.of(endTimezone))
                var current = startDateInstant
                while (current in startDateInstant..endDateInstant) {
                    val date = current.toLocalDateTime(timeZone).date
                    val existSchedule = totalSchedule[date] ?: DaySchedule(date = date)
                    totalSchedule[date] =
                        existSchedule.copy(
                            date = date,
                            scheduleList = existSchedule.scheduleList + schedule,
                        )
                    current = current.plus(1.days)
                }
            }
        }
        holidayList.forEach { holiday ->
            val date = holiday.date
            val existSchedule = totalSchedule[date] ?: DaySchedule(date = date)
            totalSchedule[date] =
                existSchedule.copy(
                    date = date,
                    holidayList = existSchedule.holidayList + holiday,
                )
        }
        anniversaryList.forEach { anniversary ->
            val date = anniversary.date
            val existSchedule = totalSchedule[date] ?: DaySchedule(date = date)
            totalSchedule[date] =
                existSchedule.copy(
                    date = date,
                    anniversaryList = existSchedule.anniversaryList + anniversary,
                )
        }
        return totalSchedule.values.sortedBy { it.date }
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
