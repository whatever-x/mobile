package com.whatever.caramel.feature.content.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState

class ContentDetailPreviewParameter : PreviewParameterProvider<ContentDetailState> {
    override val values: Sequence<ContentDetailState>
        get() =
            sequenceOf(
                ContentDetailState(
                    contentType = ContentType.CALENDAR,
                    scheduleDetail =
                        ScheduleDetail(
                            scheduleId = 0L,
                            startDateTime = "2025-07-01T10:05:17",
                            endDateTime = "2025-07-01T10:05:17",
                            startDateTimezone = "Asia/Seoul",
                            endDateTimezone = "Asia/Seoul",
                            isCompleted = false,
                            parentScheduleId = 0L,
                            title = "제목1",
                            description = "내용1",
                            tags = listOf(Tag(id = 1, label = "tag1")),
                            contentAssignee = ContentAssignee.US,
                        ),
                ),
            )
}
