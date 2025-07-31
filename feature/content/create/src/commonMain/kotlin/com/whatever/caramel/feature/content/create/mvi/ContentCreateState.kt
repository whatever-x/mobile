package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.LocalDateTime

data class ContentCreateState(
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false,
    val selectedAssignee: ContentAssigneeUiModel = ContentAssigneeUiModel.ME,
    val tags: ImmutableList<Tag> = persistentListOf(),
    val selectedTags: ImmutableSet<Tag> = persistentSetOf(),
    val createMode: CreateMode = CreateMode.MEMO,
    val showDateDialog: Boolean = false,
    val showTimeDialog: Boolean = false,
    val dateTime: LocalDateTime = DateUtil.todayLocalDateTime(),
    val showEditConfirmDialog: Boolean = false,
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
