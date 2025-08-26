package com.whatever.caramel.feature.calendar

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.policy.CalendarPolicy.MIN_YEAR
import com.whatever.caramel.core.domain.usecase.calendar.GetAnniversariesInPeriodUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetHolidayOfYearUseCase
import com.whatever.caramel.core.domain.usecase.schedule.GetScheduleInPeriodUseCase
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.toLocalDate
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarSideEffect
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.datetime.LocalDate

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
        return CalendarState()
    }

    override suspend fun handleIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.ChangeMonthPicker -> changeMonthPicker(intent)
            is CalendarIntent.ChangeYearPicker -> changeYearPicker(intent)
            is CalendarIntent.DismissDropDownMenu -> hideDropDownMenu()
            is CalendarIntent.HideDropDownMenu -> hideDropDownMenu()
            is CalendarIntent.ShowDropDownMenu -> showDropDownMenu()
            is CalendarIntent.RefreshCalendar -> refreshCalender()
            is CalendarIntent.SwipeCalendar -> swipeCalendar(intent)
        }
    }

    private fun swipeCalendar(intent: CalendarIntent.SwipeCalendar) {
        val year = MIN_YEAR + (intent.page / 12)
        val month = (intent.page % 12) + 1

        reduce {
            copy(
                currentDate = LocalDate(year = year, monthNumber = month, dayOfMonth = 1),
            )
        }
    }

    private fun refreshCalender() {
        reduce {
            copy(
                isRefreshing = true
            )
        }
    }

    private fun hideDropDownMenu() {
        reduce {
            copy(
                isShowDropDownMenu = false,
                currentDate = dateUiState.copy(day = 1).toLocalDate()
            )
        }

        postSideEffect(CalendarSideEffect.ScrollToPage(page = currentState.page))
    }

    private fun showDropDownMenu() {
        reduce {
            copy(
                isShowDropDownMenu = true,
                dateUiState = DateUiState.fromLocalDate(localDate = currentDate)
            )
        }
    }

    private fun changeMonthPicker(intent: CalendarIntent.ChangeMonthPicker) {
        reduce {
            copy(
                dateUiState = dateUiState.copy(
                    month = intent.month
                )
            )
        }
    }

    private fun changeYearPicker(intent: CalendarIntent.ChangeYearPicker) {
        reduce {
            copy(
                dateUiState = dateUiState.copy(
                    year = intent.year
                )
            )
        }
    }

}
