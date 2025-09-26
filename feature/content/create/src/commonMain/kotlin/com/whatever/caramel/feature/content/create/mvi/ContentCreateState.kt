package com.whatever.caramel.feature.content.create.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.TimeUiState
import com.whatever.caramel.core.ui.picker.model.toLocalDate
import com.whatever.caramel.core.ui.picker.model.toLocalTime
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
    val scheduleDateType: ScheduleDateTimeType = ScheduleDateTimeType.START,
    val startDateTimeInfo: ScheduleDateTimeState = ScheduleDateTimeState(),
    val endDateTimeInfo: ScheduleDateTimeState = ScheduleDateTimeState(),
) : UiState {
    val isSaveButtonEnable: Boolean
        get() = title.isNotBlank() || content.isNotBlank()

    val pickerDateTimeInfo
        get() =
            when (scheduleDateType) {
                ScheduleDateTimeType.START -> startDateTimeInfo
                ScheduleDateTimeType.END -> endDateTimeInfo
            }
}

enum class ScheduleDateTimeType {
    START,
    END,
}

data class ScheduleDateTimeState(
    val dateUiState: DateUiState = DateUiState.currentDate(),
    val timeUiState: TimeUiState = TimeUiState.currentTime(),
) {
    val dateTime: LocalDateTime
        get() {
            val date = dateUiState.toLocalDate()
            val time = timeUiState.toLocalTime()
            return LocalDateTime(date = date, time = time)
        }

    companion object {
        fun from(dateTime: LocalDateTime) =
            ScheduleDateTimeState(
                dateUiState = DateUiState.from(dateTime),
                timeUiState = TimeUiState.from(dateTime),
            )
    }
}
