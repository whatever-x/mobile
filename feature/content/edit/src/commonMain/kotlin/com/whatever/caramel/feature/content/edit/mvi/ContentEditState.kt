package com.whatever.caramel.feature.content.edit.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.TimeUiState
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.LocalDateTime

data class ContentEditState(
    val isLoading: Boolean = false,
    val type: ContentType = ContentType.MEMO,
    val contentId: Long = -1L,
    val title: String = "",
    val content: String = "",
    val tags: ImmutableList<Tag> = persistentListOf(),
    val selectedTags: ImmutableSet<Tag> = persistentSetOf(),
    val selectedAssignee: ContentAssigneeUiModel = ContentAssigneeUiModel.ME,
    val createMode: CreateMode = if (type == ContentType.MEMO) CreateMode.MEMO else CreateMode.CALENDAR,
    val showDateDialog: Boolean = false,
    val showTimeDialog: Boolean = false,
    val dateTime: LocalDateTime = DateUtil.todayLocalDateTime(),
    val dateUiState: DateUiState = DateUiState.currentDate(),
    val timeUiState: TimeUiState = TimeUiState.currentTime(),
    val showExitConfirmDialog: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val showDeletedContentDialog: Boolean = false,
) : UiState {
    val isSaveButtonEnable: Boolean
        get() = title.isNotBlank() || content.isNotBlank()

    val date: String
        get() = "${dateTime.year}년 ${dateTime.monthNumber}월 ${dateTime.dayOfMonth}일"

    val time: String
        get() = "${dateTime.hour.toString().padStart(2, '0')}:${
            dateTime.minute.toString().padStart(2, '0')
        }"
}
