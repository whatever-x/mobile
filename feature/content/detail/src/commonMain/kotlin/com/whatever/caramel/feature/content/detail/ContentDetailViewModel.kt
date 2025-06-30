package com.whatever.caramel.feature.content.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.ContentErrorCode
import com.whatever.caramel.core.domain.exception.code.ScheduleErrorCode
import com.whatever.caramel.core.domain.usecase.calendar.DeleteScheduleUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetScheduleUseCase
import com.whatever.caramel.core.domain.usecase.common.GetLinkPreviewsForContentUseCase
import com.whatever.caramel.core.domain.usecase.memo.DeleteMemoUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoUseCase
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailIntent
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailSideEffect
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState
import com.whatever.caramel.feature.content.detail.navigation.ContentDetailRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

class ContentDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMemoUseCase: GetMemoUseCase,
    private val getScheduleUseCase: GetScheduleUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val getLinkPreviewsForContentUseCase: GetLinkPreviewsForContentUseCase,
) : BaseViewModel<ContentDetailState, ContentDetailSideEffect, ContentDetailIntent>(savedStateHandle) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentDetailState {
        val arguments = savedStateHandle.toRoute<ContentDetailRoute>()
        return ContentDetailState(
            contentId = arguments.contentId,
            contentType = ContentType.valueOf(arguments.type),
        )
    }

    private fun fetchLinkPreviews(content: String) {
        launch {
            getLinkPreviewsForContentUseCase(content)
                .onStart { reduce { copy(isLoadingLinkPreview = true) } }
                .catch { e ->
                    reduce { copy(isLoadingLinkPreview = false) }
                }
                .collect { linkMetaDataList ->
                    reduce {
                        copy(
                            linkMetaDataList = linkMetaDataList.toImmutableList(),
                            isLoadingLinkPreview = false,
                        )
                    }
                }
        }
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
                                ContentDetailSideEffect.ShowErrorSnackBar(
                                    message = throwable.message,
                                ),
                            )
                        ErrorUiType.DIALOG ->
                            postSideEffect(
                                ContentDetailSideEffect.ShowErrorDialog(
                                    message = throwable.message,
                                    description = throwable.description,
                                ),
                            )
                    }
                }
            }
        } else {
            postSideEffect(
                ContentDetailSideEffect.ShowErrorSnackBar(
                    message = throwable.message ?: "알 수 없는 오류가 발생했습니다.",
                ),
            )
        }
    }

    override suspend fun handleIntent(intent: ContentDetailIntent) {
        when (intent) {
            is ContentDetailIntent.ClickCloseButton -> {
                postSideEffect(ContentDetailSideEffect.NavigateToBackStack)
            }

            is ContentDetailIntent.ClickEditButton -> {
                postSideEffect(
                    ContentDetailSideEffect.NavigateToEdit(
                        currentState.contentId,
                        currentState.contentType,
                    ),
                )
            }

            is ContentDetailIntent.ClickDeleteButton -> {
                reduce { copy(showDeleteConfirmDialog = true) }
            }

            is ContentDetailIntent.ClickConfirmDeleteDialogButton -> {
                reduce { copy(showDeleteConfirmDialog = false) }
                launch {
                    try {
                        when (currentState.contentType) {
                            ContentType.MEMO -> deleteMemoUseCase(currentState.contentId)
                            ContentType.CALENDAR -> deleteScheduleUseCase(currentState.contentId)
                        }
                        postSideEffect(ContentDetailSideEffect.NavigateToBackStack)
                    } catch (e: Exception) {
                        handleClientException(e)
                    }
                }
            }

            is ContentDetailIntent.ClickCancelDeleteDialogButton -> {
                reduce { copy(showDeleteConfirmDialog = false) }
            }

            ContentDetailIntent.DismissDeletedContentDialog -> {
                handleDismissDeletedContentDialog()
            }

            ContentDetailIntent.LoadDataOnStart -> {
                loadContentDetails()
            }

            ContentDetailIntent.ClickBackButton -> {
                postSideEffect(ContentDetailSideEffect.NavigateToBackStack)
            }
        }
    }

    private fun handleDismissDeletedContentDialog() {
        reduce { copy(showDeletedContentDialog = false) }
        postSideEffect(ContentDetailSideEffect.NavigateToBackStack)
    }

    private fun loadContentDetails() {
        launch {
            reduce { copy(isLoading = true) }
            when (currentState.contentType) {
                ContentType.MEMO -> {
                    val memo = getMemoUseCase(currentState.contentId)
                    reduce { copy(memoDetail = memo, isLoading = false) }

                    fetchLinkPreviews(memo.description)
                }

                ContentType.CALENDAR -> {
                    val schedule = getScheduleUseCase(currentState.contentId)
                    reduce { copy(scheduleDetail = schedule, isLoading = false) }
                    schedule.description?.also {
                        fetchLinkPreviews(it)
                    }
                }
            }
        }
    }
}
