package com.whatever.caramel.feature.content.create

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.params.content.ContentParameterType
import com.whatever.caramel.core.domain.params.content.memo.MemoParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleParameter
import com.whatever.caramel.core.domain.usecase.content.CreateContentUseCase
import com.whatever.caramel.core.domain.usecase.content.GetAllTagsUseCase
import com.whatever.caramel.core.domain.validator.ContentValidator
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.TimeUiState
import com.whatever.caramel.core.ui.picker.model.toLocalDate
import com.whatever.caramel.core.util.DateUtil
import com.whatever.caramel.core.util.codePointCount
import com.whatever.caramel.core.util.copy
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.create.mvi.ContentCreateIntent
import com.whatever.caramel.feature.content.create.mvi.ContentCreateSideEffect
import com.whatever.caramel.feature.content.create.mvi.ContentCreateState
import com.whatever.caramel.feature.content.create.mvi.DateTimeInfo
import com.whatever.caramel.feature.content.create.mvi.ScheduleDateTimeType
import com.whatever.caramel.feature.content.create.navigation.ContentCreateRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.hours

class ContentCreateViewModel(
    crashlytics: CaramelCrashlytics,
    savedStateHandle: SavedStateHandle,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val createContentUseCase: CreateContentUseCase,
) : BaseViewModel<ContentCreateState, ContentCreateSideEffect, ContentCreateIntent>(
        savedStateHandle,
        crashlytics,
    ) {
    init {
        launch {
            val tags = getAllTagsUseCase()
            reduce {
                copy(tags = tags.toImmutableList())
            }
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentCreateState {
        val arguments = savedStateHandle.toRoute<ContentCreateRoute>()
        val defaultTimeZone = TimeZone.currentSystemDefault()
        val isDateTimeEmpty = arguments.dateTimeString.isEmpty()
        val initInstant: Instant =
            if (isDateTimeEmpty) {
                DateUtil.todayLocalDateTime().copy(minute = 0)
            } else {
                LocalDateTime.parse(arguments.dateTimeString).copy()
            }.toInstant(defaultTimeZone)

        val (startDateTime, endDateTime) =
            if (isDateTimeEmpty) {
                initInstant.plus(1.hours).toLocalDateTime(defaultTimeZone) to
                    initInstant.plus(2.hours).toLocalDateTime(defaultTimeZone)
            } else {
                initInstant.toLocalDateTime(defaultTimeZone) to
                    initInstant.plus(1.hours).toLocalDateTime(defaultTimeZone)
            }
        return ContentCreateState(
            createMode =
                when (ContentType.valueOf(arguments.contentType)) {
                    ContentType.MEMO -> CreateMode.MEMO
                    ContentType.CALENDAR -> CreateMode.CALENDAR
                },
            startDateTimeInfo =
                DateTimeInfo(
                    dateTime = startDateTime,
                    dateUiState = DateUiState.from(dateTime = startDateTime),
                    timeUiState = TimeUiState.from(dateTime = startDateTime),
                ),
            endDateTimeInfo =
                DateTimeInfo(
                    dateTime = endDateTime,
                    dateUiState = DateUiState.from(dateTime = endDateTime),
                    timeUiState = TimeUiState.from(dateTime = endDateTime),
                ),
        )
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        ContentCreateSideEffect.ShowToast(
                            message = throwable.message,
                        ),
                    )

                ErrorUiType.DIALOG ->
                    postSideEffect(
                        ContentCreateSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                ContentCreateSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
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
            is ContentCreateIntent.ClickDate -> handleDateClick(intent)
            is ContentCreateIntent.ClickTime -> handleTimeClick(intent)
            is ContentCreateIntent.ClickEditDialogRightButton -> clickEditDialogRightButton(intent)
            is ContentCreateIntent.ClickEditDialogLeftButton -> clickEditDialogLeftButton(intent)
            is ContentCreateIntent.ClickAssignee -> clickAssignee(intent)
            is ContentCreateIntent.ClickCompleteButton -> clickComplete(intent)
            ContentCreateIntent.ClickAllDayButton -> clickAllDay()
        }
    }

    private fun clickAllDay() {
        reduce { copy(isAllDay = !isAllDay) }
    }

    private fun clickComplete(intent: ContentCreateIntent.ClickCompleteButton) {
        val localDate = currentState.recentDateTimeInfo.dateUiState.toLocalDate()
        val localTime =
            with(currentState.recentDateTimeInfo.timeUiState) {
                val hour = this.hour.toInt()
                val minute = this.minute.toInt()
                val convertedHour =
                    when (period) {
                        "오후" -> if (hour == 12) 12 else hour + 12 // 오후 : 12 ~ 23
                        "오전" -> if (hour == 12) 0 else hour // 오전 : 00 ~ 11
                        else -> throw CaramelException(
                            code = AppErrorCode.UNKNOWN,
                            message = "알 수 없는 오류 입니다.",
                            debugMessage = "잘못된 Period",
                            errorUiType = ErrorUiType.TOAST,
                        )
                    }
                LocalTime(hour = convertedHour, minute = minute)
            }
        val localDateTime = localDate.atTime(time = localTime)
        val updatedDateTimeInfo = currentState.recentDateTimeInfo.copy(dateTime = localDateTime)

        reduce {
            copy(
                showDateDialog = false,
                showTimeDialog = false,
            )
        }
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun clickAssignee(intent: ContentCreateIntent.ClickAssignee) {
        reduce {
            copy(selectedAssignee = intent.assignee)
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
        val validatedTitle =
            ContentValidator
                .checkInputTitleValidate(
                    input = intent.text,
                    inputLength = intent.text.codePointCount(),
                ).getOrThrow()

        reduce {
            copy(title = validatedTitle)
        }
    }

    private fun inputContent(intent: ContentCreateIntent.InputContent) {
        val validatedBody =
            ContentValidator
                .checkInputBodyValidate(
                    input = intent.text,
                    inputLength = intent.text.codePointCount(),
                ).getOrThrow()

        reduce {
            copy(content = validatedBody)
        }
    }

    private fun toggleTagSelection(intent: ContentCreateIntent.ClickTag) {
        reduce {
            copy(
                selectedTags =
                    if (selectedTags.contains(intent.tag)) {
                        selectedTags - intent.tag
                    } else {
                        selectedTags + intent.tag
                    }.toImmutableSet(),
            )
        }
    }

    private fun selectCreateMode(intent: ContentCreateIntent.SelectCreateMode) {
        reduce {
            copy(createMode = intent.createMode)
        }
    }

    private fun handleDateClick(intent: ContentCreateIntent.ClickDate) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                dateUiState = DateUiState.from(dateTime = currentState.recentDateTimeInfo.dateTime),
            )
        reduce {
            copy(scheduleDateType = intent.type, showDateDialog = true)
        }
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun handleTimeClick(intent: ContentCreateIntent.ClickTime) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                timeUiState = TimeUiState.from(dateTime = currentState.recentDateTimeInfo.dateTime),
            )
        reduce {
            copy(scheduleDateType = intent.type)
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
            copy(showTimeDialog = true)
        }
    }

    private fun hideDateTimeDialog(intent: ContentCreateIntent.HideDateTimeDialog) {
        reduce {
            copy(showDateDialog = false, showTimeDialog = false)
        }
    }

    private fun updateYear(intent: ContentCreateIntent.OnYearChanged) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                dateUiState = currentState.recentDateTimeInfo.dateUiState.copy(year = intent.year),
            )
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun updateMonth(intent: ContentCreateIntent.OnMonthChanged) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                dateUiState = currentState.recentDateTimeInfo.dateUiState.copy(month = intent.month),
            )
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun updateDay(intent: ContentCreateIntent.OnDayChanged) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                dateUiState = currentState.recentDateTimeInfo.dateUiState.copy(day = intent.day),
            )
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun updateMinute(intent: ContentCreateIntent.OnMinuteChanged) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                timeUiState = currentState.recentDateTimeInfo.timeUiState.copy(minute = intent.minute),
            )
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun updateHour(intent: ContentCreateIntent.OnHourChanged) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                timeUiState = currentState.recentDateTimeInfo.timeUiState.copy(hour = intent.hour),
            )
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun updatePeriod(intent: ContentCreateIntent.OnPeriodChanged) {
        val updatedDateTimeInfo =
            currentState.recentDateTimeInfo.copy(
                timeUiState = currentState.recentDateTimeInfo.timeUiState.copy(period = intent.period),
            )
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = updatedDateTimeInfo)
                ScheduleDateTimeType.NONE -> this
            }
        }
    }

    private fun clickSaveButton(intent: ContentCreateIntent.ClickSaveButton) {
        launch {
            val state = currentState
            val parameter =
                when (state.createMode) {
                    CreateMode.MEMO ->
                        ContentParameterType.Memo(
                            MemoParameter(
                                title = state.title.ifBlank { null },
                                description = state.content.ifBlank { null },
                                isCompleted = false,
                                tags = state.selectedTags.map { it.id }.toList(),
                                contentAssignee =
                                    ContentAssignee.valueOf(
                                        value = state.selectedAssignee.name,
                                    ),
                            ),
                        )

                    CreateMode.CALENDAR -> {
                        val defaultTimeZone = TimeZone.currentSystemDefault().id
                        val startDateTime =
                            state.startDateTimeInfo.dateTime.withAllDayTime(isStart = true)
                        val endDateTime =
                            state.endDateTimeInfo.dateTime.withAllDayTime(isStart = false)
                        ContentParameterType.Calendar(
                            ScheduleParameter(
                                title = state.title.ifBlank { null },
                                description = state.content.ifBlank { null },
                                isCompleted = false,
                                startDateTime = startDateTime.toString(),
                                startTimeZone = defaultTimeZone,
                                endDateTime = endDateTime.toString(),
                                endTimeZone = defaultTimeZone,
                                tagIds = state.selectedTags.map { it.id }.toList(),
                                contentAssignee =
                                    ContentAssignee.valueOf(
                                        value = state.selectedAssignee.name,
                                    ),
                            ),
                        )
                    }
                }
            createContentUseCase(parameter)
            postSideEffect(ContentCreateSideEffect.NavigateToBackStack)
        }
    }

    private fun LocalDateTime.withAllDayTime(isStart: Boolean) =
        if (currentState.isAllDay) {
            copy(hour = if (isStart) 0 else 23, minute = if (isStart) 0 else 59)
        } else {
            this
        }
}
