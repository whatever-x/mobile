package com.whatever.caramel.feature.content.detail.mvi

import com.whatever.caramel.core.domain.entity.Memo
import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.calendar.ScheduleDetail
import com.whatever.caramel.core.domain.vo.common.LinkMetaData
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.content.CreateMode
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
    val linkMetaDataList: ImmutableList<LinkMetaData> = persistentListOf(),
    val isLoadingLinkPreview: Boolean = false,
) : UiState {

    val title: String
        get() = when (contentType) {
            ContentType.MEMO -> memoDetail?.title ?: ""
            ContentType.CALENDAR -> scheduleDetail?.title ?: ""
        }

    val description: String
        get() = when (contentType) {
            ContentType.MEMO -> memoDetail?.description ?: ""
            ContentType.CALENDAR -> scheduleDetail?.description ?: ""
        }

    val tags: ImmutableList<Tag>
        get() = when (contentType) {
            ContentType.MEMO -> memoDetail?.tagList?.toImmutableList() ?: persistentListOf()
            ContentType.CALENDAR -> scheduleDetail?.tags?.toImmutableList() ?: persistentListOf()
        }

    val createMode: CreateMode
        get() = when (contentType) {
            ContentType.MEMO -> CreateMode.MEMO
            ContentType.CALENDAR -> CreateMode.CALENDAR
        }

    val dateTime: LocalDateTime?
        get() = if (contentType == ContentType.CALENDAR) {
            scheduleDetail?.startDateTime?.let { LocalDateTime.parse(it) }
        } else {
            null // Memo only has createdAt (LocalDate)
        }

    val date: String?
        get() = when (contentType) {
            ContentType.CALENDAR -> dateTime?.let { "${it.year}년 ${it.monthNumber}월 ${it.dayOfMonth}일" }
            ContentType.MEMO -> memoDetail?.createdAt?.let { "${it.year}년 ${it.monthNumber}월 ${it.dayOfMonth}일" }
        }

    val time: String?
        get() = if (contentType == ContentType.CALENDAR) {
            dateTime?.let {
                "${it.hour.toString().padStart(2, '0')}:${
                    it.minute.toString().padStart(2, '0')
                }"
            }
        } else {
            null // Memo does not have a specific time component for this display
        }
} 