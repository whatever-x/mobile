package com.whatever.caramel.feature.content.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.code.ContentErrorCode
import com.whatever.caramel.core.domain.exception.code.ScheduleErrorCode
import com.whatever.caramel.core.domain.params.content.memo.MemoEditParameter
import com.whatever.caramel.core.domain.params.content.schdule.ScheduleEditParameter
import com.whatever.caramel.core.domain.usecase.content.GetAllTagsUseCase
import com.whatever.caramel.core.domain.usecase.memo.DeleteMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.UpdateMemoUseCase
import com.whatever.caramel.core.domain.usecase.schedule.DeleteScheduleUseCase
import com.whatever.caramel.core.domain.usecase.schedule.GetScheduleUseCase
import com.whatever.caramel.core.domain.usecase.schedule.UpdateScheduleUseCase
import com.whatever.caramel.core.domain.validator.ContentValidator
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.content.schedule.DateTimeInfo
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.TimeUiState
import com.whatever.caramel.core.ui.picker.model.toLocalDate
import com.whatever.caramel.core.util.codePointCount
import com.whatever.caramel.core.util.copy
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.edit.mvi.ContentEditIntent
import com.whatever.caramel.feature.content.edit.mvi.ContentEditSideEffect
import com.whatever.caramel.feature.content.edit.mvi.ContentEditState
import com.whatever.caramel.feature.content.edit.mvi.ScheduleDateTimeState
import com.whatever.caramel.feature.content.edit.mvi.ScheduleDateTimeType
import com.whatever.caramel.feature.content.edit.navigation.ContentEditScreenRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime

class ContentEditViewModel(
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val getMemoUseCase: GetMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val getScheduleUseCase: GetScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
) : BaseViewModel<ContentEditState, ContentEditSideEffect, ContentEditIntent>(
        savedStateHandle = savedStateHandle,
        caramelCrashlytics = crashlytics,
    ) {
    init {
        loadContent()
        loadTags()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentEditState {
        val arguments = savedStateHandle.toRoute<ContentEditScreenRoute>()
        return ContentEditState(
            contentId = arguments.id,
            type = ContentType.valueOf(arguments.type),
        )
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.code) {
                ContentErrorCode.CONTENT_NOT_FOUND, ScheduleErrorCode.SCHEDULE_NOT_FOUND -> {
                    reduce { copy(showDeletedContentDialog = true) }
                }

                else -> {
                    when (throwable.errorUiType) {
                        ErrorUiType.TOAST ->
                            postSideEffect(
                                ContentEditSideEffect.ShowErrorSnackBar(
                                    message = throwable.message,
                                ),
                            )

                        ErrorUiType.DIALOG ->
                            postSideEffect(
                                ContentEditSideEffect.ShowErrorDialog(
                                    message = throwable.message,
                                    description = throwable.description,
                                ),
                            )
                    }
                }
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                ContentEditSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
        }
    }

    override suspend fun handleIntent(intent: ContentEditIntent) {
        when (intent) {
            is ContentEditIntent.OnTitleChanged -> handleOnTitleChanged(intent)
            is ContentEditIntent.OnContentChanged -> handleOnContentChanged(intent)
            is ContentEditIntent.ClickTag -> toggleTagSelection(intent)
            is ContentEditIntent.ClickDate -> handleDateClick(intent)
            is ContentEditIntent.ClickTime -> handleTimeClick(intent)
            ContentEditIntent.HideDateTimeDialog ->
                reduce {
                    copy(
                        showDateDialog = false,
                        showTimeDialog = false,
                    )
                }

            is ContentEditIntent.OnYearChanged -> updateYear(intent)
            is ContentEditIntent.OnMonthChanged -> updateMonth(intent)
            is ContentEditIntent.OnDayChanged -> updateDay(intent)
            is ContentEditIntent.OnPeriodChanged -> updatePeriod(intent)
            is ContentEditIntent.OnHourChanged -> updateHour(intent)
            is ContentEditIntent.OnMinuteChanged -> updateMinute(intent)
            ContentEditIntent.OnSaveClicked -> handleOnSaveClicked()
            ContentEditIntent.OnDeleteClicked -> handleOnDeleteClicked()
            ContentEditIntent.OnBackClicked -> handleOnBackClicked()
            ContentEditIntent.DismissExitDialog -> handleDismissExitDialog()
            ContentEditIntent.ConfirmExitDialog -> handleConfirmExitDialog()
            ContentEditIntent.DismissDeleteDialog -> handleDismissDeleteDialog()
            ContentEditIntent.ConfirmDeleteDialog -> handleConfirmDeleteDialog()
            ContentEditIntent.DismissDeletedContentDialog -> handleDismissDeletedContentDialog()
            is ContentEditIntent.OnCreateModeSelected -> handleOnCreateModeSelected(intent)
            is ContentEditIntent.ClickAssignee -> clickAssignee(intent)
            is ContentEditIntent.ClickCompleteButton -> clickComplete(intent)
            ContentEditIntent.ClickAllDayButton -> clickAllDayButton()
        }
    }

    private fun handleTimeClick(intent: ContentEditIntent.ClickTime) {
        val targetDateTimeInfo =
            when (intent.type) {
                ScheduleDateTimeType.START -> currentState.startDateTimeInfo
                ScheduleDateTimeType.END -> currentState.endDateTimeInfo
            }
        val updatedDateTimeInfo =
            targetDateTimeInfo.copy(
                timeUiState = TimeUiState.from(dateTime = targetDateTimeInfo.dateTime),
            )
        reduce {
            when (intent.type) {
                ScheduleDateTimeType.START ->
                    copy(
                        startDateTimeInfo = updatedDateTimeInfo,
                        showTimeDialog = true,
                        scheduleDateType = intent.type,
                    )

                ScheduleDateTimeType.END ->
                    copy(
                        endDateTimeInfo = updatedDateTimeInfo,
                        showTimeDialog = true,
                        scheduleDateType = intent.type,
                    )
            }
        }
    }

    private fun handleDateClick(intent: ContentEditIntent.ClickDate) {
        val targetDateTimeInfo =
            when (intent.type) {
                ScheduleDateTimeType.START -> currentState.startDateTimeInfo
                ScheduleDateTimeType.END -> currentState.endDateTimeInfo
            }
        val updatedDateTimeInfo =
            targetDateTimeInfo.copy(
                dateUiState = DateUiState.from(dateTime = targetDateTimeInfo.dateTime),
            )
        reduce {
            when (intent.type) {
                ScheduleDateTimeType.START ->
                    copy(
                        startDateTimeInfo = updatedDateTimeInfo,
                        showDateDialog = true,
                        scheduleDateType = intent.type,
                    )

                ScheduleDateTimeType.END ->
                    copy(
                        endDateTimeInfo = updatedDateTimeInfo,
                        showDateDialog = true,
                        scheduleDateType = intent.type,
                    )
            }
        }
    }

    private fun clickAllDayButton() {
        reduce { copy(isAllDay = !isAllDay) }
    }

    private fun clickComplete(intent: ContentEditIntent.ClickCompleteButton) {
        val localDate = currentState.pickerDateTimeInfo.dateUiState.toLocalDate()
        val localTime =
            with(currentState.pickerDateTimeInfo.timeUiState) {
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
        when (currentState.scheduleDateType) {
            ScheduleDateTimeType.START -> {
                if (currentState.endDateTimeInfo.dateTime < localDateTime) {
                    postSideEffect(ContentEditSideEffect.ShowErrorSnackBar("시작이 종료보다 늦을 수 없어요"))
                } else {
                    reduce {
                        copy(
                            startDateTimeInfo = ScheduleDateTimeState.from(localDateTime),
                            showDateDialog = false,
                            showTimeDialog = false,
                        )
                    }
                }
            }
            ScheduleDateTimeType.END -> {
                if (currentState.startDateTimeInfo.dateTime > localDateTime) {
                    postSideEffect(ContentEditSideEffect.ShowErrorSnackBar("종료는 시작보다 빠를 수 없어요"))
                } else {
                    reduce {
                        copy(
                            endDateTimeInfo = ScheduleDateTimeState.from(localDateTime),
                            showDateDialog = false,
                            showTimeDialog = false,
                        )
                    }
                }
            }
        }
    }

    private fun clickAssignee(intent: ContentEditIntent.ClickAssignee) {
        reduce {
            copy(selectedAssignee = intent.assignee)
        }
    }

    private fun handleOnTitleChanged(intent: ContentEditIntent.OnTitleChanged) {
        val validatedTitle =
            ContentValidator
                .checkInputTitleValidate(
                    input = intent.title,
                    inputLength = intent.title.codePointCount(),
                ).getOrThrow()

        reduce {
            copy(title = validatedTitle)
        }
    }

    private fun handleOnContentChanged(intent: ContentEditIntent.OnContentChanged) {
        val validatedBody =
            ContentValidator
                .checkInputBodyValidate(
                    input = intent.content,
                    inputLength = intent.content.codePointCount(),
                ).getOrThrow()

        reduce {
            copy(content = validatedBody)
        }
    }

    private fun toggleTagSelection(intent: ContentEditIntent.ClickTag) {
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

    private suspend fun handleOnSaveClicked() {
        val state = currentState
        val defaultTimeZone = TimeZone.currentSystemDefault().id
        launch {
            when (state.type) {
                ContentType.MEMO -> {
                    updateMemoUseCase(
                        memoId = state.contentId,
                        parameter =
                            MemoEditParameter(
                                title = state.title.ifBlank { null },
                                description = state.content.ifBlank { null },
                                isCompleted = null,
                                tagIds = state.selectedTags.map { it.id }.toList(),
                                dateTimeInfo =
                                    if (state.createMode == CreateMode.CALENDAR) {
                                        DateTimeInfo(
                                            startDateTime =
                                                state.startDateTimeInfo.dateTime.withAllDayTime(
                                                    isStart = true,
                                                ),
                                            startTimezone = defaultTimeZone,
                                            endDateTime =
                                                state.endDateTimeInfo.dateTime.withAllDayTime(
                                                    isStart = false,
                                                ),
                                            endTimezone = defaultTimeZone,
                                        )
                                    } else {
                                        null
                                    },
                                contentAssignee = ContentAssignee.valueOf(value = state.selectedAssignee.name),
                            ),
                    )
                }

                ContentType.CALENDAR -> {
                    updateScheduleUseCase(
                        scheduleId = state.contentId,
                        parameter =
                            ScheduleEditParameter(
                                selectedDate = state.startDateTimeInfo.dateTime.toString(),
                                title = state.title.ifBlank { null },
                                description = state.content.ifBlank { null },
                                isCompleted = false,
                                dateTimeInfo =
                                    if (state.createMode == CreateMode.CALENDAR) {
                                        DateTimeInfo(
                                            startDateTime =
                                                state.startDateTimeInfo.dateTime.withAllDayTime(
                                                    isStart = true,
                                                ),
                                            startTimezone = defaultTimeZone,
                                            endDateTime =
                                                state.endDateTimeInfo.dateTime.withAllDayTime(
                                                    isStart = false,
                                                ),
                                            endTimezone = defaultTimeZone,
                                        )
                                    } else {
                                        null
                                    },
                                tagIds = state.selectedTags.map { it.id }.toList(),
                                contentAssignee = ContentAssignee.valueOf(value = state.selectedAssignee.name),
                            ),
                    )
                }
            }
            postSideEffect(ContentEditSideEffect.NavigateBackToContentList)
        }
    }

    private fun handleOnDeleteClicked() {
        reduce { copy(showDeleteConfirmDialog = true) }
    }

    private fun handleOnBackClicked() {
        reduce { copy(showExitConfirmDialog = true) }
    }

    private fun handleDismissExitDialog() {
        reduce { copy(showExitConfirmDialog = false) }
    }

    private fun handleConfirmExitDialog() {
        postSideEffect(ContentEditSideEffect.NavigateBack)
    }

    private fun handleDismissDeleteDialog() {
        reduce { copy(showDeleteConfirmDialog = false) }
    }

    private fun handleDismissDeletedContentDialog() {
        reduce { copy(showDeletedContentDialog = false) }
        postSideEffect(ContentEditSideEffect.NavigateBackToContentList)
    }

    private fun handleConfirmDeleteDialog() {
        launch {
            when (currentState.type) {
                ContentType.MEMO -> {
                    deleteMemoUseCase(currentState.contentId)
                    postSideEffect(ContentEditSideEffect.NavigateBackToContentList)
                }

                ContentType.CALENDAR -> {
                    deleteScheduleUseCase(currentState.contentId)
                    postSideEffect(ContentEditSideEffect.NavigateBackToContentList)
                }
            }
        }
    }

    private fun handleOnCreateModeSelected(intent: ContentEditIntent.OnCreateModeSelected) {
        reduce { copy(createMode = intent.mode) }
    }

    private fun updateYear(intent: ContentEditIntent.OnYearChanged) {
        updateDateTimeInfo { copy(dateUiState = dateUiState.copy(year = intent.year)) }
    }

    private fun updateMonth(intent: ContentEditIntent.OnMonthChanged) {
        updateDateTimeInfo { copy(dateUiState = dateUiState.copy(month = intent.month)) }
    }

    private fun updateDay(intent: ContentEditIntent.OnDayChanged) {
        updateDateTimeInfo { copy(dateUiState = dateUiState.copy(day = intent.day)) }
    }

    private fun updateMinute(intent: ContentEditIntent.OnMinuteChanged) {
        updateDateTimeInfo { copy(timeUiState = timeUiState.copy(minute = intent.minute)) }
    }

    private fun updateHour(intent: ContentEditIntent.OnHourChanged) {
        updateDateTimeInfo { copy(timeUiState = timeUiState.copy(hour = intent.hour)) }
    }

    private fun updatePeriod(intent: ContentEditIntent.OnPeriodChanged) {
        updateDateTimeInfo { copy(timeUiState = timeUiState.copy(period = intent.period)) }
    }

    private fun loadContent() {
        launch {
            reduce { copy(isLoading = true) }
            when (currentState.type) {
                ContentType.MEMO -> {
                    val memo = getMemoUseCase(currentState.contentId)
                    reduce {
                        copy(
                            isLoading = false,
                            title = memo.contentData.title,
                            content = memo.contentData.description,
                            selectedTags = memo.tagList.toImmutableSet(),
                            selectedAssignee = ContentAssigneeUiModel.valueOf(value = memo.contentData.contentAssignee.name),
                        )
                    }
                }

                ContentType.CALENDAR -> {
                    val schedule = getScheduleUseCase(currentState.contentId)
                    reduce {
                        copy(
                            isLoading = false,
                            title = schedule.contentData.title,
                            content = schedule.contentData.description,
                            isAllDay =
                                checkAllDay(
                                    schedule.dateTimeInfo.startDateTime,
                                    schedule.dateTimeInfo.endDateTime,
                                ),
                            selectedTags = schedule.tagList.toImmutableSet(),
                            startDateTimeInfo = ScheduleDateTimeState.from(schedule.dateTimeInfo.startDateTime),
                            endDateTimeInfo = ScheduleDateTimeState.from(schedule.dateTimeInfo.endDateTime),
                            selectedAssignee = ContentAssigneeUiModel.valueOf(value = schedule.contentData.contentAssignee.name),
                        )
                    }
                }
            }
        }
    }

    private fun loadTags() {
        launch {
            val tags = getAllTagsUseCase()
            reduce { copy(tags = tags.toImmutableList()) }
        }
    }

    private fun LocalDateTime.withAllDayTime(isStart: Boolean) =
        if (currentState.isAllDay) {
            copy(hour = if (isStart) 0 else 23, minute = if (isStart) 0 else 59)
        } else {
            this
        }

    private fun checkAllDay(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Boolean = startDateTime.hour == 0 && startDateTime.minute == 0 && endDateTime.hour == 23 && endDateTime.minute == 59

    private inline fun updateDateTimeInfo(crossinline transform: ScheduleDateTimeState.() -> ScheduleDateTimeState) {
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = startDateTimeInfo.transform())
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = endDateTimeInfo.transform())
            }
        }
    }
}
