package com.whatever.caramel.feature.content.create

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.usecase.memo.CreateContentUseCase
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.domain.vo.calendar.ScheduleParameter
import com.whatever.caramel.core.domain.vo.content.ContentParameterType
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.memo.MemoParameter
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.util.copy
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.create.mvi.ContentCreateIntent
import com.whatever.caramel.feature.content.create.mvi.ContentCreateSideEffect
import com.whatever.caramel.feature.content.create.mvi.ContentCreateState
import com.whatever.caramel.feature.content.create.navigation.ContentCreateRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ContentCreateViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTagUseCase: GetTagUseCase,
    private val createContentUseCase: CreateContentUseCase
) : BaseViewModel<ContentCreateState, ContentCreateSideEffect, ContentCreateIntent>(savedStateHandle) {

    init {
        launch {
            val tags = getTagUseCase()
            reduce {
                copy(tags = tags.toImmutableList())
            }
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentCreateState {
        val arguments = savedStateHandle.toRoute<ContentCreateRoute>()
        val dateTime =
            if (arguments.dateTimeString.isEmpty()) DateUtil.todayLocalDateTime() else LocalDateTime.parse(
                arguments.dateTimeString
            )
        return ContentCreateState(
            createMode = when (arguments.contentType) {
                ContentType.MEMO -> CreateMode.MEMO
                ContentType.CALENDAR -> CreateMode.CALENDAR
            },
            dateTime = dateTime
        )
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST -> postSideEffect(
                    ContentCreateSideEffect.ShowToast(
                        message = throwable.message
                    )
                )
                ErrorUiType.DIALOG -> postSideEffect(
                    ContentCreateSideEffect.ShowErrorDialog(
                        message = throwable.message,
                        description = throwable.description
                    )
                )
            }
        } else {
            postSideEffect(
                ContentCreateSideEffect.ShowToast(
                    message = throwable.message ?: "알 수 없는 오류가 발생했습니다."
                )
            )
        }
    }

    override suspend fun handleIntent(intent: ContentCreateIntent) {
        when (intent) {
            is ContentCreateIntent.ClickCloseButton -> clickCloseButton(intent)
            is ContentCreateIntent.ClickSaveButton -> clickSaveButton(intent)
            is ContentCreateIntent.InputTitle -> inputTitle(intent)
            is ContentCreateIntent.InputContent -> inputContent(intent)
            is ContentCreateIntent.ClickTag -> toggleTagSelection(intent)
            is ContentCreateIntent.SelectCreateMode -> selectCreateMode(intent)
            is ContentCreateIntent.HideDateTimeDialog -> hideDateTimeDialog(intent)
            is ContentCreateIntent.OnYearChanged -> updateYear(intent)
            is ContentCreateIntent.OnMonthChanged -> updateMonth(intent)
            is ContentCreateIntent.OnDayChanged -> updateDay(intent)
            is ContentCreateIntent.OnPeriodChanged -> updatePeriod(intent)
            is ContentCreateIntent.OnHourChanged -> updateHour(intent)
            is ContentCreateIntent.OnMinuteChanged -> updateMinute(intent)
            is ContentCreateIntent.ClickDate -> clickDate(intent)
            is ContentCreateIntent.ClickTime -> clickTime(intent)
            ContentCreateIntent.ClickEditDialogRightButton -> clickEditDialogRightButton(intent)
            ContentCreateIntent.ClickEditDialogLeftButton -> clickEditDialogLeftButton(intent)
        }
    }

    private fun clickEditDialogLeftButton(intent: ContentCreateIntent) {
        postSideEffect(ContentCreateSideEffect.NavigateToBackStack)
    }

    private fun clickEditDialogRightButton(intent: ContentCreateIntent) {
        reduce {
            copy(showEditConfirmDialog = false)
        }
    }

    private fun clickCloseButton(intent: ContentCreateIntent.ClickCloseButton) {
        reduce {
            copy(showEditConfirmDialog = true)
        }
    }

    private fun inputTitle(intent: ContentCreateIntent.InputTitle) {
        if (intent.text.length <= ContentCreateState.MAX_TITLE_LENGTH) {
            reduce {
                copy(
                    title = intent.text
                )
            }
        } else {
            postSideEffect(ContentCreateSideEffect.ShowToast("제목은 ${ContentCreateState.MAX_TITLE_LENGTH}자까지 입력할 수 있어요"))
        }
    }

    private fun inputContent(intent: ContentCreateIntent.InputContent) {
        if (intent.text.length <= ContentCreateState.MAX_CONTENT_LENGTH) {
            reduce {
                copy(
                    content = intent.text
                )
            }
        } else {
            postSideEffect(ContentCreateSideEffect.ShowToast("내용은 ${ContentCreateState.MAX_CONTENT_LENGTH}자까지 입력할 수 있어요"))
        }
    }

    private fun toggleTagSelection(intent: ContentCreateIntent.ClickTag) {
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

    private fun selectCreateMode(intent: ContentCreateIntent.SelectCreateMode) {
        reduce {
            copy(
                createMode = intent.createMode,
                dateTime = if (intent.createMode == CreateMode.CALENDAR) {
                    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val roundedMinute = ((now.minute + 2) / 5) * 5
                    val adjustedHour = if (roundedMinute >= 60) now.hour + 1 else now.hour
                    val finalMinute = if (roundedMinute >= 60) 0 else roundedMinute

                    now.copy(
                        hour = adjustedHour,
                        minute = finalMinute,
                        second = 0,
                        nanosecond = 0
                    )
                } else {
                    dateTime
                }
            )
        }
    }

    private fun clickDate(intent: ContentCreateIntent.ClickDate) {
        reduce {
            copy(showDateDialog = true)
        }
    }

    private fun clickTime(intent: ContentCreateIntent.ClickTime) {
        reduce {
            copy(showTimeDialog = true)
        }
    }

    private fun hideDateTimeDialog(intent: ContentCreateIntent.HideDateTimeDialog) {
        reduce {
            copy(showDateDialog = false, showTimeDialog = false)
        }
    }

    private fun updateYear(intent: ContentCreateIntent.OnYearChanged) {
        reduce { copy(dateTime = dateTime.copy(year = intent.year)) }
    }

    private fun updateMonth(intent: ContentCreateIntent.OnMonthChanged) {
        reduce { copy(dateTime = dateTime.copy(monthNumber = intent.month)) }
    }

    private fun updateDay(intent: ContentCreateIntent.OnDayChanged) {
        reduce { copy(dateTime = dateTime.copy(dayOfMonth = intent.day)) }
    }

    private fun updateMinute(intent: ContentCreateIntent.OnMinuteChanged) {
        val minute = intent.minute.toIntOrNull() ?: currentState.dateTime.minute
        reduce { copy(dateTime = dateTime.copy(minute = minute)) }
    }

    private fun updateHour(intent: ContentCreateIntent.OnHourChanged) {
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

    private fun updatePeriod(intent: ContentCreateIntent.OnPeriodChanged) {
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

    private fun clickSaveButton(intent: ContentCreateIntent.ClickSaveButton) {
        launch {
            val state = currentState
            val parameter = when (state.createMode) {
                CreateMode.MEMO -> ContentParameterType.Memo(
                    MemoParameter(
                        title = state.title.ifBlank { null },
                        description = state.content.ifBlank { null },
                        isCompleted = false,
                        tags = state.selectedTags.map { it.id }.toList()
                    )
                )

                CreateMode.CALENDAR -> ContentParameterType.Calendar(
                    ScheduleParameter(
                        title = state.title.ifBlank { null },
                        description = state.content.ifBlank { null },
                        isCompleted = false,
                        startDateTime = state.dateTime.toString(),
                        startTimeZone = TimeZone.currentSystemDefault().id,
                        endDateTime = null,
                        endTimeZone = null,
                        tagIds = state.selectedTags.map { it.id }.toList()
                    )
                )
            }
            createContentUseCase(parameter)
            postSideEffect(ContentCreateSideEffect.NavigateToBackStack)
        }
    }
}
