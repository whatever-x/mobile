package com.whatever.caramel.feature.content.detail.mvi

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Schedule
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.content.LinkMetaData
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class ContentDetailState(
    val contentId: Long = -1L,
    val contentType: ContentType,
    val isLoading: Boolean = true,
    val memoDetail: Memo? = null,
    val scheduleDetail: Schedule? = null,
    val showDeleteConfirmDialog: Boolean = false,
    val showDeletedContentDialog: Boolean = false,
    val linkMetaDataList: ImmutableList<LinkMetaData> = persistentListOf(),
    val isLoadingLinkPreview: Boolean = false,
) : UiState {
    val title: String
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.contentData?.title?.ifEmpty { memoDetail.contentData.description } ?: ""
                ContentType.CALENDAR -> scheduleDetail?.contentData?.title?.ifEmpty { scheduleDetail.contentData.description } ?: ""
            }

    val description: String
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.contentData?.description?.takeIf { memoDetail.contentData.title.isNotEmpty() } ?: ""
                ContentType.CALENDAR ->
                    scheduleDetail?.contentData?.description?.takeIf { scheduleDetail.contentData.title.isNotEmpty() }
                        ?: ""
            }

    val tags: ImmutableList<Tag>
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.tagList?.toImmutableList() ?: persistentListOf()
                ContentType.CALENDAR -> scheduleDetail?.tagList?.toImmutableList() ?: persistentListOf()
            }

    val contentAssignee: ContentAssignee
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.contentData?.contentAssignee ?: ContentAssignee.US
                ContentType.CALENDAR -> scheduleDetail?.contentData?.contentAssignee ?: ContentAssignee.US
            }

    val tagString: String
        get() = tags.joinToString(", ") { it.label }

    val isAllDay: Boolean
        get() =
            scheduleDetail?.let {
                it.dateTimeInfo.run {
                    startDateTime.hour == 0 && startDateTime.minute == 0 && endDateTime.hour == 23 && endDateTime.minute == 59
                }
            } ?: false
}
