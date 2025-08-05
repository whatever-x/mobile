package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import kotlinx.datetime.LocalDate

internal fun HolidayDetailListResponse.toHolidayList(): List<Holiday> =
    this.holidayList.map {
        Holiday(
            date = LocalDate.parse(it.date),
            name = it.name,
            isHoliday = it.isHoliday,
        )
    }
