package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.AnniversaryType
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.remote.dto.calendar.HolidayDetailListResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleAnniversaryResponse
import kotlinx.datetime.LocalDate

internal fun HolidayDetailListResponse.toHolidayList(): List<Holiday> =
    this.holidayList.map {
        Holiday(
            date = LocalDate.parse(it.date),
            name = it.name,
            isHoliday = it.isHoliday,
        )
    }

internal fun CoupleAnniversaryResponse.toAnniversary(): List<Anniversary> {
    val hundredDayAnniversaries =
        this.hundredDayAnniversaries.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }

    val yearlyAnniversaries =
        this.yearlyAnniversaries.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }

    val myBirthdayAnniversaries =
        this.myBirthDates.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }

    val partnerBirthdayAnniversaries =
        this.partnerBirthDates.map {
            Anniversary(
                date = LocalDate.parse(it.date),
                type = AnniversaryType.valueOf(it.type),
                label = it.label,
            )
        }
    return hundredDayAnniversaries + yearlyAnniversaries + myBirthdayAnniversaries + partnerBirthdayAnniversaries
}
