package com.whatever.caramel.feature.content.create

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.usecase.memo.CreateContentUseCase
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.domain.vo.calendar.ScheduleParameter
import com.whatever.caramel.core.domain.vo.item.ContentParameterType
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.create.components.CreateMode
import com.whatever.caramel.feature.content.create.mvi.ContentIntent
import com.whatever.caramel.feature.content.create.mvi.ContentSideEffect
import com.whatever.caramel.feature.content.create.mvi.ContentState
import com.whatever.caramel.feature.content.create.navigation.ContentRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class ContentViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTagUseCase: GetTagUseCase,
    private val createContentUseCase: CreateContentUseCase
) : BaseViewModel<ContentState, ContentSideEffect, ContentIntent>(savedStateHandle) {

    init {
        launch {
            val tags = getTagUseCase()
            reduce {
                copy(tags = tags.toImmutableList())
            }
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentState {
        val arguments = savedStateHandle.toRoute<ContentRoute>()
        return ContentState(
            id = arguments.contentId
        )
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        val (code, message) = if (throwable is CaramelException) {
            throwable.code to throwable.message
        } else {
            AppErrorCode.UNKNOWN to null
        }
        postSideEffect(ContentSideEffect.ShowErrorSnackBar(code = code, message = message))
    }

    override suspend fun handleIntent(intent: ContentIntent) {
        when (intent) {
            is ContentIntent.ClickCloseButton -> clickCloseButton(intent)
            is ContentIntent.ClickSaveButton -> clickSaveButton(intent)
            is ContentIntent.InputTitle -> inputTitle(intent)
            is ContentIntent.InputContent -> inputContent(intent)
            is ContentIntent.ClickTag -> toggleTagSelection(intent)
            is ContentIntent.SelectCreateMode -> selectCreateMode(intent)
            is ContentIntent.HideDateTimeDialog -> hideDateTimeDialog(intent)
            is ContentIntent.OnYearChanged -> updateYear(intent)
            is ContentIntent.OnMonthChanged -> updateMonth(intent)
            is ContentIntent.OnDayChanged -> updateDay(intent)
            is ContentIntent.OnPeriodChanged -> updatePeriod(intent)
            is ContentIntent.OnHourChanged -> updateHour(intent)
            is ContentIntent.OnMinuteChanged -> updateMinute(intent)
            is ContentIntent.ClickDate -> clickDate(intent)
            is ContentIntent.ClickTime -> clickTime(intent)
            ContentIntent.ClickEditDialogRightButton -> clickEditDialogRightButton(intent)
            ContentIntent.ClickEditDialogLeftButton -> clickEditDialogLeftButton(intent)
        }
    }

    private fun clickEditDialogLeftButton(intent: ContentIntent) {
        postSideEffect(ContentSideEffect.NavigateToBackStack)
    }

    private fun clickEditDialogRightButton(intent: ContentIntent) {
        reduce {
            copy(showEditConfirmDialog = false)
        }
    }

    private fun clickCloseButton(intent: ContentIntent.ClickCloseButton) {
        reduce {
            copy(showEditConfirmDialog = true)
        }
    }

    private fun inputTitle(intent: ContentIntent.InputTitle) {
        if (intent.text.length <= ContentState.MAX_TITLE_LENGTH) {
            reduce {
                copy(
                    title = intent.text
                )
            }
        }
    }

    private fun inputContent(intent: ContentIntent.InputContent) {
        if (intent.text.length <= ContentState.MAX_CONTENT_LENGTH) {
            reduce {
                copy(
                    content = intent.text
                )
            }
        }
    }

    private fun toggleTagSelection(intent: ContentIntent.ClickTag) {
        reduce {
            copy(
                selectedTags = if (selectedTags.contains(intent.tag)) {
                    selectedTags - intent.tag
                } else {
                    selectedTags + intent.tag
                }.toImmutableSet()
            )
        }
    }

    private fun selectCreateMode(intent: ContentIntent.SelectCreateMode) {
        reduce {
            copy(
                createMode = intent.createMode,
            )
        }
    }

    private fun clickDate(intent: ContentIntent.ClickDate) {
        reduce {
            copy(showDateDialog = true)
        }
    }

    private fun clickTime(intent: ContentIntent.ClickTime) {
        reduce {
            copy(showTimeDialog = true)
        }
    }

    private fun hideDateTimeDialog(intent: ContentIntent.HideDateTimeDialog) {
        reduce {
            copy(showDateDialog = false, showTimeDialog = false)
        }
    }

    private fun updateYear(intent: ContentIntent.OnYearChanged) {
        reduce { copy(dateTime = dateTime.copy(year = intent.year)) }
    }

    private fun updateMonth(intent: ContentIntent.OnMonthChanged) {
        reduce { copy(dateTime = dateTime.copy(monthNumber = intent.month)) }
    }

    private fun updateDay(intent: ContentIntent.OnDayChanged) {
        reduce { copy(dateTime = dateTime.copy(dayOfMonth = intent.day)) }
    }

    private fun updateMinute(intent: ContentIntent.OnMinuteChanged) {
        val minute = intent.minute.toIntOrNull() ?: currentState.dateTime.minute
        reduce { copy(dateTime = dateTime.copy(minute = minute)) }
    }

    private fun updateHour(intent: ContentIntent.OnHourChanged) {
        val newHour12 = intent.hour.toIntOrNull() ?: return

        reduce {
            val currentDateTime = dateTime
            val currentHour24 = currentDateTime.hour
            val newHour24 = when {
                currentHour24 < 12 -> { // 현재 AM
                    if (newHour12 == 12) 0 else newHour12 // 12 AM은 0시, 나머지는 그대로
                }

                else -> { // 현재 PM
                    if (newHour12 == 12) 12 else newHour12 + 12 // 12 PM은 12시, 나머지는 +12
                }
            }
            copy(dateTime = currentDateTime.copy(hour = newHour24))
        }
    }

    private fun updatePeriod(intent: ContentIntent.OnPeriodChanged) {
        reduce {
            val currentDateTime = dateTime
            val currentHour24 = currentDateTime.hour
            val finalNewHour24 = when (intent.period) {
                "오전" -> if (currentHour24 >= 12) currentHour24 - 12 else currentHour24 // PM -> AM
                "오후" -> if (currentHour24 < 12) currentHour24 + 12 else currentHour24  // AM -> PM
                else -> currentHour24
            }
            copy(dateTime = currentDateTime.copy(hour = finalNewHour24))
        }
    }

    private fun clickSaveButton(intent: ContentIntent.ClickSaveButton) {
        launch {
            val state = currentState
            val parameter = when (state.createMode) {
                ContentState.CreateMode.MEMO -> ContentParameterType.Memo(
                    MemoParameter(
                        title = state.title,
                        description = state.content,
                        isCompleted = false,
                        tags = state.selectedTags.map { it.id }.toList()
                    )
                )

                ContentState.CreateMode.CALENDAR -> ContentParameterType.Calendar(
                    ScheduleParameter(
                        title = state.title,
                        description = state.content,
                        isCompleted = false,
                        startDateTime = state.dateTime.toInstant(TimeZone.currentSystemDefault())
                            .toString(),
                        startTimeZone = TimeZone.currentSystemDefault().id,
                        endDateTime = null,
                        endTimeZone = null,
                        tagIds = state.selectedTags.map { it.id }.toList()
                    )
                )
            }
            createContentUseCase(parameter)
            postSideEffect(ContentSideEffect.NavigateToBackStack)
        }
    }
}

private fun LocalDateTime.copy(
    year: Int = this.year,
    monthNumber: Int = this.monthNumber,
    dayOfMonth: Int = this.dayOfMonth,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
    nanosecond: Int = this.nanosecond
): LocalDateTime {
    return LocalDateTime(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = dayOfMonth,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond
    )
}