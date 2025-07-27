package com.whatever.caramel.feature.content.detail.mvi

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail
import com.whatever.caramel.core.domain.vo.common.LinkMetaData
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime

data class ContentDetailState(
    val contentId: Long = -1L,
    val contentType: ContentType,
    val isLoading: Boolean = true,
    val memoDetail: Memo? = null,
    val scheduleDetail: ScheduleDetail? = null,
    val showDeleteConfirmDialog: Boolean = false,
    val showDeletedContentDialog: Boolean = false,
    val linkMetaDataList: ImmutableList<LinkMetaData> = persistentListOf(),
    val isLoadingLinkPreview: Boolean = false,
) : UiState {
    val title: String
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.title?.ifEmpty { memoDetail.description } ?: ""
                ContentType.CALENDAR -> scheduleDetail?.title?.ifEmpty { scheduleDetail.description } ?: ""
            }

    val description: String
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.description?.takeIf { memoDetail.title.isNotEmpty() } ?: ""
                ContentType.CALENDAR -> scheduleDetail?.description?.takeIf { scheduleDetail.title.isNotEmpty() } ?: ""
            }

    val tags: ImmutableList<Tag>
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.tagList?.toImmutableList() ?: persistentListOf()
                ContentType.CALENDAR -> scheduleDetail?.tags?.toImmutableList() ?: persistentListOf()
            }

    val date: String
        get() {
            val dateTime = scheduleDetail?.startDateTime ?: ""
            val parsed = LocalDateTime.parse(dateTime)

            return "${parsed.year}년 ${parsed.monthNumber}월 ${parsed.dayOfMonth}일"
        }

    val role : ContentAssignee
        get() = when(contentType) {
            ContentType.MEMO -> memoDetail?.role ?: ContentAssignee.US
            ContentType.CALENDAR -> scheduleDetail?.role ?: ContentAssignee.US
        }

    val time: String
        get() {
            val dateTime = scheduleDetail?.startDateTime ?: ""
            val parsed = LocalDateTime.parse(dateTime)
            val hour = parsed.hour.toString().padStart(2, '0')
            val minute = parsed.minute.toString().padStart(2, '0')

            return "$hour:$minute"
        }

    val tagString : String
        get() = tags.joinToString(", ") { it.label }

    val existsContent: Boolean
        get() {
            return when(contentType) {
                ContentType.MEMO -> {
                    memoDetail != null && (memoDetail.description.isNotEmpty() || memoDetail.tagList.isNotEmpty())
                }
                ContentType.CALENDAR -> scheduleDetail != null
            }
        }
}
