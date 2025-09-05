package com.whatever.caramel.feature.calendar.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.policy.CalendarPolicy
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.number

internal class CalendarScreenPreviewData : PreviewParameterProvider<CalendarState> {
    private val year = 2025
    private val month = Month.JULY

    override val values: Sequence<CalendarState>
        get() =
            sequenceOf(
                CalendarState(
                    year,
                    month,
                    currentDates(),
                    pageIndex(),
                    isShowDatePicker = false,
                    bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
                    scheduleBottomSheetList = emptyList(),
                ),
                CalendarState(
                    year,
                    month,
                    currentDates(),
                    pageIndex(),
                    isShowDatePicker = true,
                    bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
                    scheduleBottomSheetList = emptyList(),
                ),
                CalendarState(
                    year,
                    month,
                    currentDates(),
                    pageIndex(),
                    isShowDatePicker = false,
                    bottomSheetState = BottomSheetState.EXPANDED,
                    scheduleBottomSheetList = emptyList(),
                ),
            )

    private fun currentDates(): List<LocalDate> =
        (1..DateUtil.getLastDayOfMonth(year, month.number))
            .map { LocalDate(year, month, it) }

    private fun pageIndex(): Int {
        val yearRange = CalendarPolicy.MIN_YEAR..CalendarPolicy.MAX_YEAR
        return yearRange.indexOf(year) * 12 + (month.number - 1)
    }

    private fun date(day: Int) = LocalDate(year, month, day)

    private fun dateTime(
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
    ) = LocalDateTime(year, month, day, hour, minute)

    private fun yearSchedule(): List<Schedule> {
        var id = 1L

        fun todo(
            day: Int,
            title: String,
            contentAssignee: ContentAssignee,
        ) = Schedule(
            id = id++,
            contentData =
                ContentData(
                    title = title,
                    description = "$title 내용",
                    isCompleted = false,
                    contentAssignee = contentAssignee,
                ),
            dateTimeInfo =
                DateTimeInfo(
                    startDateTime = dateTime(day),
                    startTimezone = "",
                    endDateTime = dateTime(day, 23, 59),
                    endTimezone = "",
                ),
            tagList = emptyList(),
        )

        return emptyList()
    }
}
