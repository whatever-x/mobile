package com.whatever.caramel.core.domain.model.calendar

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class CalendarModel(
    val id: Long,
    val year: Int,
    val month: Int,
    var day: List<CalendarDayModel>
) {
    private fun getDaysInMonth(year: Int, month: Int) = when (month) {
        2 -> if (isLeapYear(year)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }

    companion object {
        // @RyuSw-cs 2025.03.10 샘플 캘린더 모델 생성 함수
        fun createSampleCalendarModel(year: Int, month: Int): CalendarModel {
            val daysInMonth = when (month) {
                2 -> if (isLeapYear(year)) 29 else 28
                4, 6, 9, 11 -> 30
                else -> 31
            }
            val updatedDays = List(daysInMonth) { index ->
                CalendarDayModel(
                    id = 0L,
                    day = index + 1,
                    isNationalHoliday = false,
                    weekDay = LocalDate(year, month, index + 1).dayOfWeek,
                    todos = listOf(
                        CalendarTodoModel(
                            id = 0L,
                            type = CalendarTodoType.ANNIVERSARY,
                            description = "ANNIVERSARY"
                        ),
                        CalendarTodoModel(
                            id = 0L,
                            type = CalendarTodoType.NATIONAL_HOLIDAY,
                            description = "NATIONAL_HOLIDAY"
                        ),
                        CalendarTodoModel(
                            id = 0L,
                            type = CalendarTodoType.TODO,
                            description = "TODO"
                        )
                    )
                )
            }

            return CalendarModel(
                id = 0L,
                year = year,
                month = month,
                day = updatedDays.toList()
            )
        }

        fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
        }
    }
}

data class CalendarDayModel(
    val id: Long,
    val day: Int,
    val isNationalHoliday: Boolean,
    val weekDay: DayOfWeek,
    val todos: List<CalendarTodoModel>
) {
    fun isHoliday(): Boolean {
        return weekDay == DayOfWeek.SUNDAY || isNationalHoliday
    }
}

data class CalendarTodoModel(
    val id: Long,
    val type: CalendarTodoType,
    val description: String,
)

enum class CalendarTodoType(val priority: Int) {
    NATIONAL_HOLIDAY(1), ANNIVERSARY(2), TODO(3), OVER(4)
}

