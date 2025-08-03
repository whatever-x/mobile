package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.Holiday

interface CalendarRepository {

    suspend fun getHolidays(year: Int): List<Holiday>

    suspend fun getAnniversaryList(
        coupleId: Long,
        startDate: String,
        endDate: String,
    ): List<Anniversary>

}
