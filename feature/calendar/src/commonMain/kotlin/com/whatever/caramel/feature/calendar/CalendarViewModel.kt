package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.CalendarDayState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class CalendarViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CalendarState, CalendarSideEffect, CalendarIntent>(savedStateHandle) {

    init {
        val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        loadCalendar(current.year, current.month.ordinal + 1, current.dayOfMonth)
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): CalendarState {
        return CalendarState()
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.ClickTodo -> postSideEffect(
                CalendarSideEffect.NavigateToTodoDetail(
                    intent.todoId
                )
            )

            is CalendarIntent.ToggleDate -> toggleDatePicker(intent.year, intent.month)
            is CalendarIntent.ToggleDay -> {
                selectDay(intent.selectedDay)
                toggleTodoBottomSheet(
                    intent.month,
                    intent.dayList
                )
            }

            is CalendarIntent.DismissDatePicker -> dismissDatePicker(intent.year, intent.month, intent.isInitPage)
            is CalendarIntent.SwipeCalendar -> swipeCalendar(intent.pageIndex, intent.isInitPage)
        }
    }

    private fun loadCalendar(year: Int, month: Int, day: Int = 1) {
        launch {
            if (day != 1) {
                reduce {
                    copy(today = day)
                }
            }

            reduce {
                copy(isLoading = true)
            }
            // @RyuSw-cs 2025.03.31 FIXME : 캘린더 API로 변경 필요
            val calendarResponse = CalendarModel.createSampleCalendarModel(year, month)
            reduce {
                copy(
                    isLoading = false,
                    selectedYear = calendarResponse.year,
                    selectedMonth = calendarResponse.month,
                    datePickerState = datePickerState.copy(
                        isOpen = false,
                        selectedYear = year,
                        selectedMonth = month
                    ),
                    calendarDays = calendarResponse.day.mapIndexed { index, dayModel ->
                        CalendarDayState(
                            isFocused = day == dayModel.day,
                            dayOfWeek = dayModel.weekDay,
                            isHoliday = dayModel.isHoliday,
                            isSaturday = dayModel.isSaturday,
                            day = index + 1,
                            todos = dayModel.todos.sortedBy { it.type.priority }
                        )
                    }
                )
            }
        }
    }

    private fun swipeCalendar(pageIndex: Int, isInitPage: Boolean) {
        launch {
            val calcDate = state.value.calcYearAndMonthByPageCount(pageIndex)
            reduce {
                copy(
                    selectedYear = calcDate.first,
                    selectedMonth = calcDate.second,
                )
            }
            if (isInitPage) {
                loadCalendar(calcDate.first, calcDate.second, state.value.today)
            } else {
                loadCalendar(calcDate.first, calcDate.second)
            }

        }
    }

    private fun toggleDatePicker(year: Int, month: Int) {
        launch {
            reduce {
                copy(
                    datePickerState = datePickerState.copy(
                        isOpen = !state.value.datePickerState.isOpen,
                        selectedYear = year,
                        selectedMonth = month
                    )
                )
            }
        }
    }

    private fun dismissDatePicker(year: Int, month: Int, isInitDate : Boolean) {
        launch {
            reduce {
                copy(
                    datePickerState = datePickerState.copy(
                        isOpen = false,
                        selectedYear = year,
                        selectedMonth = month
                    )
                )
            }
            if (isInitDate) {
                loadCalendar(year, month, state.value.today)
            } else {
                loadCalendar(year, month)
            }
        }
    }

    private fun selectDay(day: Int) {
        reduce {
            copy(
                calendarDays = calendarDays.map { dayState ->
                    if (dayState.day == day) {
                        dayState.copy(isFocused = true)
                    } else {
                        dayState.copy(isFocused = false)
                    }
                }
            )
        }
    }

    private fun toggleTodoBottomSheet(
        month: Int,
        dayList: List<CalendarDayState>,
    ) {
        launch {
            reduce {
                copy(
                    todoSheetState = todoSheetState.copy(
                        isOpen = !todoSheetState.isOpen,
                        month = month,
                        // @RyuSw-cs 2025.03.31 FIXME : Todo 바텀시트 연동 시 구현
                        dayList = emptyList()
                    )
                )
            }
        }
    }
}
