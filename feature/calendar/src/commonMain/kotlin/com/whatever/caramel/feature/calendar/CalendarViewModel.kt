package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.CalendarDayState
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState

class CalendarViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CalendarState, CalendarSideEffect, CalendarIntent>(savedStateHandle) {

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

            is CalendarIntent.DismissDatePicker -> dismissDatePicker(intent.year, intent.month)
            is CalendarIntent.SwipeCalendar -> swipeCalendar(intent.pageIndex)
        }
    }

    fun loadCalendar(year: Int, month: Int, day: Int = 1) {
        launch {
            reduce {
                copy(isLoading = true)
            }
            // FIXME : 캘린더 API로 변경 필요
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
                            isFocused = day == index + 1,
                            dayOfWeek = dayModel.weekDay,
                            isHoliday = dayModel.isHoliday(),
                            isSaturday = dayModel.isSaturday(),
                            day = index + 1,
                            todos = dayModel.todos.sortedBy { it.type.priority }
                        )
                    }
                )
            }
        }
    }

    private fun swipeCalendar(pageIndex: Int) {
        launch {
            val calcDate = state.value.calcPage(pageIndex)
            reduce {
                copy(
                    selectedYear = calcDate.first,
                    selectedMonth = calcDate.second,
                )
            }
            loadCalendar(calcDate.first, calcDate.second)
        }
    }

    private fun toggleDatePicker(year: Int, month: Int) {
        launch {
            reduce {
                copy(
                    datePickerState = datePickerState.copy(
                        isOpen = true,
                        selectedYear = year,
                        selectedMonth = month
                    )
                )
            }
            loadCalendar(year, month)
        }
    }

    private fun dismissDatePicker(year: Int, month: Int) {
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
            loadCalendar(year, month)
        }
    }

    private fun selectDay(day: Int) {
        reduce {
            // 1개를 찾아서 그거 foucsed로 바꿔야해!
            copy(
                calendarDays = calendarDays.map { dayState ->
                    if (dayState.day == day) {
                        dayState.copy(isFocused = true)
                    } else {
                        dayState
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
                        // FIXME : Todo 바텀시트 연동 시 구현
                        dayList = emptyList()
                    )
                )
            }
        }
    }
}
