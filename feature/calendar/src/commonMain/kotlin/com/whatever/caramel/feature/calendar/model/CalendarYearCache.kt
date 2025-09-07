package com.whatever.caramel.feature.calendar.model

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import kotlinx.datetime.LocalDate

data class CalendarYearCache(
    val year: Int,
    val totalSchedule: CacheModel,
) {
    data class CacheModel(
        val scheduleList: List<Schedule> = emptyList(),
        val anniversaryList: List<Anniversary> = emptyList(),
        val holidayList: List<Holiday> = emptyList(),
    ) {
        val isEmpty: Boolean
            get() = scheduleList.isEmpty() && anniversaryList.isEmpty() && holidayList.isEmpty()

        fun find(date: LocalDate): CacheModel {
            val filterSchedule = scheduleList.filter { date in it.dateTimeInfo.startDateTime.date..it.dateTimeInfo.endDateTime.date }
            val filterAnniversary = anniversaryList.filter { it.date == date }
            val filterHoliday = holidayList.filter { it.date == date }

            return CacheModel(filterSchedule, filterAnniversary, filterHoliday)
        }
    }
}
