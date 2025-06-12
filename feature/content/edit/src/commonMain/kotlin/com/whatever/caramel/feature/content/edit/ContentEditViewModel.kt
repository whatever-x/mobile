package com.whatever.caramel.feature.content.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.code.ContentErrorCode
import com.whatever.caramel.core.domain.exception.code.ScheduleErrorCode
import com.whatever.caramel.core.domain.usecase.calendar.DeleteScheduleUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetScheduleUseCase
import com.whatever.caramel.core.domain.usecase.calendar.UpdateScheduleUseCase
import com.whatever.caramel.core.domain.usecase.memo.DeleteMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.UpdateMemoUseCase
import com.whatever.caramel.core.domain.usecase.tag.GetTagUseCase
import com.whatever.caramel.core.domain.vo.calendar.ScheduleEditParameter
import com.whatever.caramel.core.domain.vo.common.DateTimeInfo
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.memo.MemoEditParameter
import com.whatever.caramel.core.ui.content.CreateMode
import com.whatever.caramel.core.util.copy
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.edit.mvi.ContentEditIntent
import com.whatever.caramel.feature.content.edit.mvi.ContentEditSideEffect
import com.whatever.caramel.feature.content.edit.mvi.ContentEditState
import com.whatever.caramel.feature.content.edit.navigation.ContentEditScreenRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

class ContentEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTagUseCase: GetTagUseCase,
    private val getMemoUseCase: GetMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val getScheduleUseCase: GetScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
) : BaseViewModel<ContentEditState, ContentEditSideEffect, ContentEditIntent>(
    savedStateHandle = savedStateHandle,
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
        val (code, message) = if (throwable is CaramelException) {
            throwable.code to throwable.message
        } else {
            AppErrorCode.UNKNOWN to null
        }

        when (code) {
            ContentErrorCode.CONTENT_NOT_FOUND, ScheduleErrorCode.SCHEDULE_NOT_FOUND -> {
                reduce { copy(showDeletedContentDialog = true) }
            }

            else -> {
                postSideEffect(
                    ContentEditSideEffect.ShowErrorSnackBar(
                        code = code,
                        message = message
                    )
                )
            }
        }
    }

    override suspend fun handleIntent(intent: ContentEditIntent) {
        when (intent) {
            is ContentEditIntent.OnTitleChanged -> handleOnTitleChanged(intent)
            is ContentEditIntent.OnContentChanged -> handleOnContentChanged(intent)
            is ContentEditIntent.ClickTag -> toggleTagSelection(intent)
            ContentEditIntent.ClickDate -> reduce { copy(showDateDialog = true) }
            ContentEditIntent.ClickTime -> reduce { copy(showTimeDialog = true) }
            ContentEditIntent.HideDateTimeDialog -> reduce {
                copy(
                    showDateDialog = false,
                    showTimeDialog = false
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
        }
    }

    private fun handleOnTitleChanged(intent: ContentEditIntent.OnTitleChanged) {
        reduce { copy(title = intent.title) }
    }

    private fun handleOnContentChanged(intent: ContentEditIntent.OnContentChanged) {
        reduce { copy(content = intent.content) }
    }

    private fun toggleTagSelection(intent: ContentEditIntent.ClickTag) {
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

    private suspend fun handleOnSaveClicked() {
        val state = currentState
        launch {
            when (state.type) {
                ContentType.MEMO -> {
                    updateMemoUseCase(
                        memoId = state.contentId,
                        parameter = MemoEditParameter(
                            title = state.title.ifBlank { null },
                            description = state.content.ifBlank { null },
                            isCompleted = null,
                            tagIds = state.selectedTags.map { it.id }.toList(),
                            dateTimeInfo = if (state.createMode == CreateMode.CALENDAR) {
                                DateTimeInfo(
                                    startDateTime = state.dateTime.toString(),
                                    startTimezone = TimeZone.currentSystemDefault().id,
                                    endDateTime = null,
                                    endTimezone = null
                                )
                            } else null
                        )
                    )
                }

                ContentType.CALENDAR -> {
                    updateScheduleUseCase(
                        scheduleId = state.contentId,
                        parameter = ScheduleEditParameter(
                            selectedDate = state.dateTime.date.toString(),
                            title = state.title.ifBlank { null },
                            description = state.content.ifBlank { null },
                            isCompleted = false,
                            dateTimeInfo = if (state.createMode == CreateMode.CALENDAR) {
                                DateTimeInfo(
                                    startDateTime = state.dateTime.toString(),
                                    startTimezone = TimeZone.currentSystemDefault().id,
                                    endDateTime = state.dateTime.toString(),
                                    endTimezone = TimeZone.currentSystemDefault().id
                                )
                            } else null,
                            tagIds = state.selectedTags.map { it.id }.toList()
                        )
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
        reduce { copy(dateTime = dateTime.copy(year = intent.year)) }
    }

    private fun updateMonth(intent: ContentEditIntent.OnMonthChanged) {
        reduce { copy(dateTime = dateTime.copy(monthNumber = intent.month)) }
    }

    private fun updateDay(intent: ContentEditIntent.OnDayChanged) {
        reduce { copy(dateTime = dateTime.copy(dayOfMonth = intent.day)) }
    }

    private fun updateMinute(intent: ContentEditIntent.OnMinuteChanged) {
        val minute = intent.minute.toIntOrNull() ?: currentState.dateTime.minute
        reduce { copy(dateTime = dateTime.copy(minute = minute)) }
    }

    private fun updateHour(intent: ContentEditIntent.OnHourChanged) {
        val newHour12 = intent.hour.toIntOrNull() ?: return
        reduce {
            val currentDateTime = dateTime
            val currentHour24 = currentDateTime.hour
            val newHour24 = when {
                currentHour24 < 12 -> {
                    if (newHour12 == 12) 0 else newHour12
                }

                else -> {
                    if (newHour12 == 12) 12 else newHour12 + 12
                }
            }
            copy(dateTime = currentDateTime.copy(hour = newHour24))
        }
    }

    private fun updatePeriod(intent: ContentEditIntent.OnPeriodChanged) {
        reduce {
            val currentDateTime = dateTime
            val currentHour24 = currentDateTime.hour
            val finalNewHour24 = when (intent.period) {
                "오전" -> if (currentHour24 >= 12) currentHour24 - 12 else currentHour24
                "오후" -> if (currentHour24 < 12) currentHour24 + 12 else currentHour24
                else -> currentHour24
            }
            copy(dateTime = currentDateTime.copy(hour = finalNewHour24))
        }
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
                            selectedTags = memo.tagList.toImmutableSet()
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
                            dateTime = scheduleDateTime
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