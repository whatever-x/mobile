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

    val date: String
        get() {
            val dateTime = scheduleDetail?.dateTimeInfo?.startDateTime ?: return ""
            return "${dateTime.year}년 ${dateTime.monthNumber}월 ${dateTime.dayOfMonth}일"
        }

    val contentAssignee: ContentAssignee
        get() =
            when (contentType) {
                ContentType.MEMO -> memoDetail?.contentData?.contentAssignee ?: ContentAssignee.US
                ContentType.CALENDAR -> scheduleDetail?.contentData?.contentAssignee ?: ContentAssignee.US
            }

    val time: String
        get() {
            val dateTime = scheduleDetail?.dateTimeInfo?.startDateTime ?: return ""
            val hour = dateTime.hour.toString().padStart(2, '0')
            val minute = dateTime.minute.toString().padStart(2, '0')

            return "$hour:$minute"
        }

    val tagString: String
        get() = tags.joinToString(", ") { it.label }
}
