package com.whatever.caramel.feature.content.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.code.ContentErrorCode
import com.whatever.caramel.core.domain.exception.code.ScheduleErrorCode
import com.whatever.caramel.core.domain.usecase.schedule.DeleteScheduleUseCase
import com.whatever.caramel.core.domain.usecase.schedule.GetScheduleUseCase
import com.whatever.caramel.core.domain.usecase.schedule.UpdateScheduleUseCase
import com.whatever.caramel.core.domain.usecase.memo.DeleteMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.UpdateMemoUseCase
import com.whatever.caramel.core.domain.usecase.content.GetTagUseCase
import com.whatever.caramel.core.domain.validator.ContentValidator
import com.whatever.caramel.core.domain.vo.calendar.ScheduleEditParameter
import com.whatever.caramel.core.domain.vo.common.DateTimeInfo
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.core.ui.picker.model.TimeUiState
import com.whatever.caramel.core.ui.picker.model.toLocalDate
import com.whatever.caramel.core.util.codePointCount
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.edit.mvi.ContentEditIntent
import com.whatever.caramel.feature.content.edit.mvi.ContentEditSideEffect
import com.whatever.caramel.feature.content.edit.mvi.ContentEditState
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
    private val getTagUseCase: GetTagUseCase,
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
            is ContentEditIntent.ClickDate -> reduce { copy(showDateDialog = true) }
            is ContentEditIntent.ClickTime -> reduce { copy(showTimeDialog = true) }
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
        }
    }

    private fun clickComplete(intent: ContentEditIntent.ClickCompleteButton) {
        val localDate = currentState.dateUiState.toLocalDate()
        val localTime =
            with(currentState.timeUiState) {
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

        reduce {
            copy(
                showDateDialog = false,
                showTimeDialog = false,
                dateTime = localDateTime,
            )
        }
    }

    private fun clickAssignee(intent: ContentEditIntent.ClickAssignee) {
        reduce {
            copy(selectedAssignee = intent.assignee)
        }
    }

    private fun handleOnTitleChanged(intent: ContentEditIntent.OnTitleChanged) {
        val validatedTitle = ContentValidator.checkInputTitleValidate(
            input = intent.title,
            inputLength = intent.title.codePointCount()
        ).getOrThrow()

        reduce {
            copy(title = validatedTitle)
        }
    }

    private fun handleOnContentChanged(intent: ContentEditIntent.OnContentChanged) {
        val validatedBody = ContentValidator.checkInputBodyValidate(
            input = intent.content,
            inputLength = intent.content.codePointCount()
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
                                            startDateTime = state.dateTime.toString(),
                                            startTimezone = TimeZone.currentSystemDefault().id,
                                            endDateTime = null,
                                            endTimezone = null,
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
                                selectedDate = state.dateTime.date.toString(),
                                title = state.title.ifBlank { null },
                                description = state.content.ifBlank { null },
                                isCompleted = false,
                                dateTimeInfo =
                                    if (state.createMode == CreateMode.CALENDAR) {
                                        DateTimeInfo(
                                            startDateTime = state.dateTime.toString(),
                                            startTimezone = TimeZone.currentSystemDefault().id,
                                            endDateTime = state.dateTime.toString(),
                                            endTimezone = TimeZone.currentSystemDefault().id,
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
        reduce {
            copy(createMode = intent.mode)
        }
    }

    private fun updateYear(intent: ContentEditIntent.OnYearChanged) {
        reduce { copy(dateUiState = dateUiState.copy(year = intent.year)) }
    }

    private fun updateMonth(intent: ContentEditIntent.OnMonthChanged) {
        reduce { copy(dateUiState = dateUiState.copy(month = intent.month)) }
    }

    private fun updateDay(intent: ContentEditIntent.OnDayChanged) {
        reduce { copy(dateUiState = dateUiState.copy(day = intent.day)) }
    }

    private fun updateMinute(intent: ContentEditIntent.OnMinuteChanged) {
        reduce { copy(timeUiState = timeUiState.copy(minute = intent.minute)) }
    }

    private fun updateHour(intent: ContentEditIntent.OnHourChanged) {
        reduce { copy(timeUiState = timeUiState.copy(hour = intent.hour)) }
    }

    private fun updatePeriod(intent: ContentEditIntent.OnPeriodChanged) {
        reduce { copy(timeUiState = timeUiState.copy(period = intent.period)) }
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
                            title = memo.title,
                            content = memo.description,
                            selectedTags = memo.tagList.toImmutableSet(),
                            selectedAssignee = ContentAssigneeUiModel.valueOf(value = memo.contentAssignee.name),
                        )
                    }
                }

                ContentType.CALENDAR -> {
                    val schedule = getScheduleUseCase(currentState.contentId)
                    val scheduleDateTime = schedule.startDateTime.let { LocalDateTime.parse(it) }
                    reduce {
                        copy(
                            isLoading = false,
                            title = schedule.title ?: "",
                            content = schedule.description ?: "",
                            selectedTags = schedule.tags.toImmutableSet(),
                            dateTime = scheduleDateTime,
                            dateUiState = DateUiState.from(dateTime = scheduleDateTime),
                            timeUiState = TimeUiState.from(dateTime = scheduleDateTime),
                            selectedAssignee = ContentAssigneeUiModel.valueOf(value = schedule.contentAssignee.name),
                        )
                    }
                }
            }
            reduce { copy(isLoading = false) }
        }
    }

    private fun loadTags() {
        launch {
            val tags = getTagUseCase()
            reduce { copy(tags = tags.toImmutableList()) }
        }
    }
}
