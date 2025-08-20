package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.domain.entity.Tag
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
    val showEditConfirmDialog: Boolean = false,
    val isAllDay: Boolean = false,
    val scheduleDateType: ScheduleDateTimeType = ScheduleDateTimeType.NONE,
    val startDateTimeInfo: DateTimeInfo = DateTimeInfo(),
    val endDateTimeInfo: DateTimeInfo = DateTimeInfo(),
) : UiState {
    val isSaveButtonEnable: Boolean
        get() = title.isNotBlank() || content.isNotBlank()

    val pickerDateTimeInfo
        get() =
            when (scheduleDateType) {
                ScheduleDateTimeType.START, ScheduleDateTimeType.NONE -> startDateTimeInfo
                ScheduleDateTimeType.END -> endDateTimeInfo
            }
}

enum class ScheduleDateTimeType {
    START,
    END,
    NONE,
}

data class DateTimeInfo(
    val dateTime: LocalDateTime = DateUtil.todayLocalDateTime(),
    val dateUiState: DateUiState = DateUiState.currentDate(),
    val timeUiState: TimeUiState = TimeUiState.currentTime(),
)
