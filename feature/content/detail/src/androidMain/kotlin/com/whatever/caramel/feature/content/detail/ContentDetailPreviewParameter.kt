package com.whatever.caramel.feature.content.detail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentData
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState
import kotlinx.datetime.LocalDateTime

class ContentDetailPreviewParameter : PreviewParameterProvider<ContentDetailState> {
    override val values: Sequence<ContentDetailState>
        get() =
            sequenceOf(
                ContentDetailState(
                    contentType = ContentType.CALENDAR,
                    scheduleDetail =
                        Schedule(
                            id = 0L,
                            dateTimeInfo =
                                DateTimeInfo(
                                    startDateTime = LocalDateTime.parse("2025-07-01T10:05:17"),
                                    startTimezone = "Asia/Seoul",
                                    endDateTime = LocalDateTime.parse("2025-07-01T10:05:17"),
                                    endTimezone = "Asia/Seoul",
                                ),
                            contentData =
                                ContentData(
                                    isCompleted = false,
                                    title = "제목1",
                                    description = "내용1",
                                    contentAssignee = ContentAssignee.US,
                                ),
                            tagList = listOf(Tag(id = 1, label = "tag1")),
                        ),
                ),
            )
}
