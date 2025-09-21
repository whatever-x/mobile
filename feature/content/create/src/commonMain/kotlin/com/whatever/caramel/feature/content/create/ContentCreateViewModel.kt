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
import com.whatever.caramel.core.ui.content.ContentAssigneeUiModel
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
import com.whatever.caramel.feature.content.create.mvi.ScheduleDateTimeState
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
                LocalDateTime.parse(arguments.dateTimeString)
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
            title = arguments.title,
            selectedAssignee = ContentAssigneeUiModel.valueOf(arguments.contentAssignee),
            createMode =
                when (ContentType.valueOf(arguments.contentType)) {
                    ContentType.MEMO -> CreateMode.MEMO
                    ContentType.CALENDAR -> CreateMode.CALENDAR
                },
            startDateTimeInfo =
                ScheduleDateTimeState(
                    dateTime = startDateTime,
                    dateUiState = DateUiState.from(dateTime = startDateTime),
                    timeUiState = TimeUiState.from(dateTime = startDateTime),
                ),
            endDateTimeInfo =
                ScheduleDateTimeState(
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
        val updatedDateTimeInfo = currentState.pickerDateTimeInfo.copy(dateTime = localDateTime)
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START ->
                    copy(
                        startDateTimeInfo = updatedDateTimeInfo,
                        showDateDialog = false,
                        showTimeDialog = false,
                    )

                ScheduleDateTimeType.END ->
                    copy(
                        endDateTimeInfo = updatedDateTimeInfo,
                        showDateDialog = false,
                        showTimeDialog = false,
                    )
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
        reduce {
            val transform: (ScheduleDateTimeState) -> ScheduleDateTimeState = { info ->
                info.copy(dateUiState = DateUiState.from(info.dateTime))
            }
            when (intent.type) {
                ScheduleDateTimeType.START ->
                    copy(
                        startDateTimeInfo = transform(startDateTimeInfo),
                        showDateDialog = true,
                        scheduleDateType = intent.type,
                    )

                ScheduleDateTimeType.END ->
                    copy(
                        endDateTimeInfo = transform(endDateTimeInfo),
                        showDateDialog = true,
                        scheduleDateType = intent.type,
                    )
            }
        }
    }

    private fun handleTimeClick(intent: ContentCreateIntent.ClickTime) {
        reduce {
            val transform: (ScheduleDateTimeState) -> ScheduleDateTimeState = { info ->
                info.copy(timeUiState = TimeUiState.from(info.dateTime))
            }
            when (intent.type) {
                ScheduleDateTimeType.START ->
                    copy(
                        startDateTimeInfo = transform(startDateTimeInfo),
                        showTimeDialog = true,
                        scheduleDateType = intent.type,
                    )

                ScheduleDateTimeType.END ->
                    copy(
                        endDateTimeInfo = transform(endDateTimeInfo),
                        showTimeDialog = true,
                        scheduleDateType = intent.type,
                    )
            }
        }
    }

    private fun hideDateTimeDialog(intent: ContentCreateIntent.HideDateTimeDialog) {
        reduce {
            copy(showDateDialog = false, showTimeDialog = false)
        }
    }

    private fun updateYear(intent: ContentCreateIntent.OnYearChanged) {
        updateDateTimeInfo { copy(dateUiState = dateUiState.copy(year = intent.year)) }
    }

    private fun updateMonth(intent: ContentCreateIntent.OnMonthChanged) {
        updateDateTimeInfo { copy(dateUiState = dateUiState.copy(month = intent.month)) }
    }

    private fun updateDay(intent: ContentCreateIntent.OnDayChanged) {
        updateDateTimeInfo { copy(dateUiState = dateUiState.copy(day = intent.day)) }
    }

    private fun updateMinute(intent: ContentCreateIntent.OnMinuteChanged) {
        updateDateTimeInfo { copy(timeUiState = timeUiState.copy(minute = intent.minute)) }
    }

    private fun updateHour(intent: ContentCreateIntent.OnHourChanged) {
        updateDateTimeInfo { copy(timeUiState = timeUiState.copy(hour = intent.hour)) }
    }

    private fun updatePeriod(intent: ContentCreateIntent.OnPeriodChanged) {
        updateDateTimeInfo { copy(timeUiState = timeUiState.copy(period = intent.period)) }
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

    private inline fun updateDateTimeInfo(crossinline transform: ScheduleDateTimeState.() -> ScheduleDateTimeState) {
        reduce {
            when (currentState.scheduleDateType) {
                ScheduleDateTimeType.START -> copy(startDateTimeInfo = startDateTimeInfo.transform())
                ScheduleDateTimeType.END -> copy(endDateTimeInfo = endDateTimeInfo.transform())
            }
        }
    }
}
