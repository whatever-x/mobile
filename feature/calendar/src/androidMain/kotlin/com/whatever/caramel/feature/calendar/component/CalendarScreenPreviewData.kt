package com.whatever.caramel.feature.calendar.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.policy.CalendarPolicy
import com.whatever.caramel.core.domain.vo.calendar.Anniversary
import com.whatever.caramel.core.domain.vo.calendar.AnniversaryType
import com.whatever.caramel.core.domain.vo.calendar.Holiday
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.number

internal class CalendarScreenPreviewData : PreviewParameterProvider<CalendarState> {

    override val values: Sequence<CalendarState>
        get() {
            return sequenceOf(
                CalendarState()
            )
        }

}
