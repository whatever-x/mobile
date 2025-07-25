package com.whatever.caramel.feature.content.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail
import com.whatever.caramel.core.domain.vo.content.ContentRole
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState

@Composable
@Preview
private fun ContentDetailScreenPreview() {
    CaramelTheme {
        ContentDetailScreen(
            state =
                ContentDetailState(
                    contentType = ContentType.CALENDAR,
                    scheduleDetail =
                        ScheduleDetail(
                            scheduleId = 0L,
                            startDateTime = "2025-07-01T10:05:17",
                            endDateTime = "qwdasd",
                            startDateTimezone = "timezone",
                            endDateTimezone = "TODO()",
                            isCompleted = true,
                            parentScheduleId = 0L,
                            title = "test",
                            description = "asdasdsa",
                            tags = emptyList(),
                            role = ContentRole.BOTH
                        ),
                ),
            onIntent = {},
        )
    }
}
