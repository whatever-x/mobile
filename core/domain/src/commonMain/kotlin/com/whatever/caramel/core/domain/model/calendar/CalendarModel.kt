package com.whatever.caramel.core.domain.model.calendar

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class CalendarModel(
    val id: Long,
    val year: Int,
    val month: Int,
    var day: List<CalendarDayModel>,
) {
    companion object {
        val DAYS_OF_WEEK = listOf("일", "월", "화", "수", "목", "금", "토")
        val MIN_YEAR = 1900
        val MAX_YEAR = 2100
        val YEAR_RANGE = MIN_YEAR..MAX_YEAR
        val MONTH_RANGE = 1..12

        private fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
        }

        fun getWeekCnt() = DAYS_OF_WEEK.size

        fun getDayOfMonth(year: Int, month: Int) = when (month) {
            2 -> if (isLeapYear(year)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        // @RyuSw-cs 2025.03.13 FIXME : API 생성 후 삭제 예정
        fun createSampleCalendarModel(year: Int, month: Int): CalendarModel {
            val updatedDays = List(getDayOfMonth(year, month)) { index ->
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
    }
}

data class CalendarDayModel(
    val id: Long,
    val day: Int,
    val isNationalHoliday: Boolean,
    val weekDay: DayOfWeek,
    val todos: List<CalendarTodoModel>,
) {
    fun isHoliday(): Boolean {
        return weekDay == DayOfWeek.SUNDAY || isNationalHoliday
    }

    fun isSaturday(): Boolean {
        return weekDay == DayOfWeek.SATURDAY
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

