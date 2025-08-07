package com.whatever.caramel.feature.calendar.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Holiday
import com.whatever.caramel.core.domain.entity.Todo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.couple.Anniversary
import com.whatever.caramel.core.domain.vo.couple.AnniversaryType
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import com.whatever.caramel.feature.calendar.mvi.DaySchedule
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
                    yearSchedule = yearSchedule(),
                ),
                CalendarState(
                    year,
                    month,
                    currentDates(),
                    pageIndex(),
                    isShowDatePicker = true,
                    bottomSheetState = BottomSheetState.PARTIALLY_EXPANDED,
                    yearSchedule = yearSchedule(),
                ),
                CalendarState(
                    year,
                    month,
                    currentDates(),
                    pageIndex(),
                    isShowDatePicker = false,
                    bottomSheetState = BottomSheetState.EXPANDED,
                    yearSchedule = yearSchedule(),
                ),
            )

    private fun currentDates(): List<LocalDate> =
        (1..DateUtil.getLastDayOfMonth(year, month.number))
            .map { LocalDate(year, month, it) }

    private fun pageIndex(): Int = Calendar.YEAR_RANGE.indexOf(year) * 12 + (month.number - 1)

    private fun date(day: Int) = LocalDate(year, month, day)

    private fun dateTime(
        day: Int,
        hour: Int = 0,
        minute: Int = 0,
    ) = LocalDateTime(year, month, day, hour, minute)

    private fun yearSchedule(): List<DaySchedule> {
        var id = 1L

        fun todo(
            day: Int,
            title: String,
            contentAssignee: ContentAssignee,
        ) = Todo(
            id = id++,
            title = title,
            startDate = dateTime(day),
            endDate = dateTime(day, 23, 59),
            description = "$title 내용",
            contentAssignee = contentAssignee,
        )

        return listOf(
            DaySchedule(date = date(1), todos = listOf(todo(1, "내 일정", ContentAssignee.ME))),
            DaySchedule(date = date(2), todos = listOf(todo(2, "커플 일정", ContentAssignee.US))),
            DaySchedule(date = date(3), todos = listOf(todo(3, "상대 일정", ContentAssignee.PARTNER))),
            DaySchedule(
                date = date(4),
                holidays =
                    listOf(
                        Holiday(id = id++, date = date(4), name = "휴일", isHoliday = true),
                    ),
            ),
            DaySchedule(
                date = date(5),
                anniversaries =
                    listOf(
                        Anniversary(
                            type = AnniversaryType.BIRTHDAY,
                            date = date(5),
                            label = "생일",
                            isAdjustedForNonLeapYear = false,
                        ),
                    ),
            ),
        )
    }
}
