package com.whatever.caramel.feature.content.edit.mvi

import com.whatever.caramel.core.domain.entity.Tag
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.TimeUiState
import com.whatever.caramel.core.ui.picker.model.toLocalDate
import com.whatever.caramel.core.ui.picker.model.toLocalTime
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.util.copy
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.hours

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
    val showExitConfirmDialog: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val showDeletedContentDialog: Boolean = false,
    val isAllDay: Boolean = false,
    val scheduleDateType: ScheduleDateTimeType = ScheduleDateTimeType.START,
    val startDateTimeInfo: ScheduleDateTimeState = ScheduleDateTimeState.fromNow(),
    val endDateTimeInfo: ScheduleDateTimeState = ScheduleDateTimeState.fromNow(plusHours = 1),
    val pickerDateTimeInfo: ScheduleDateTimeState = ScheduleDateTimeState.fromNow(),
) : UiState {
    val isSaveButtonEnable: Boolean
        get() = title.isNotBlank() || content.isNotBlank()
}

enum class ScheduleDateTimeType {
    START,
    END,
}

data class ScheduleDateTimeState(
    val dateUiState: DateUiState,
    val timeUiState: TimeUiState,
) {
    val dateTime: LocalDateTime
        get() {
            val date = dateUiState.toLocalDate()
            val time = timeUiState.toLocalTime()
            return LocalDateTime(date = date, time = time)
        }

    companion object {
        fun fromNow(plusHours: Int = 0): ScheduleDateTimeState {
            val timeZone = TimeZone.currentSystemDefault()
            val now = DateUtil.todayLocalDateTime().copy(minute = 0).toInstant(timeZone)
            val nowPlusHours = now.plus(plusHours.hours)
            return from(nowPlusHours.toLocalDateTime(timeZone))
        }

        fun from(localDateTime: LocalDateTime) =
            ScheduleDateTimeState(
                dateUiState = DateUiState.from(localDateTime),
                timeUiState = TimeUiState.from(localDateTime),
            )
    }
}
