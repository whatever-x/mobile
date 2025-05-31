package com.whatever.caramel.feature.content.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
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
    private val getLinkPreviewsForContentUseCase: GetLinkPreviewsForContentUseCase
) : BaseViewModel<ContentDetailState, ContentDetailSideEffect, ContentDetailIntent>(savedStateHandle) {

    init {
        loadContentDetails()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): ContentDetailState {
        val arguments = savedStateHandle.toRoute<ContentDetailRoute>()
        return ContentDetailState(
            contentId = arguments.contentId,
            contentType = ContentType.valueOf(arguments.type),
        )
    }

    private fun loadContentDetails() {
        launch {
            val state = currentState
            reduce { copy(isLoading = true) }
            try {
                when (state.contentType) {
                    ContentType.MEMO -> {
                        val memo = getMemoUseCase(state.contentId)
                        reduce { copy(memoDetail = memo, isLoading = false) }

                        fetchLinkPreviews(memo.description)
                    }

                    ContentType.CALENDAR -> {
                        val schedule = getScheduleUseCase(state.contentId)
                        reduce { copy(scheduleDetail = schedule, isLoading = false) }
                        schedule.description?.also {
                            fetchLinkPreviews(it)
                        }
                    }
                }
            } catch (e: Exception) {
                handleClientException(e)
                reduce { copy(isLoading = false) }
            }
        }
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
                            isLoadingLinkPreview = false
                        )
                    }
                }
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        val (code, message) = if (throwable is CaramelException) {
            throwable.code to throwable.message
        } else {
            AppErrorCode.UNKNOWN to null
        }
        postSideEffect(ContentDetailSideEffect.ShowErrorSnackBar(code = code, message = message))
    }

    override suspend fun handleIntent(intent: ContentDetailIntent) {
        val state = currentState
        when (intent) {
            is ContentDetailIntent.ClickCloseButton -> {
                postSideEffect(ContentDetailSideEffect.NavigateToBackStack)
            }

            is ContentDetailIntent.ClickEditButton -> {
                postSideEffect(
                    ContentDetailSideEffect.NavigateToEdit(
                        state.contentId,
                        state.contentType
                    )
                )
            }

            is ContentDetailIntent.ClickDeleteButton -> {
                reduce { copy(showDeleteConfirmDialog = true) }
            }

            is ContentDetailIntent.ClickConfirmDeleteDialogButton -> {
                reduce { copy(showDeleteConfirmDialog = false) }
                launch {
                    try {
                        when (state.contentType) {
                            ContentType.MEMO -> deleteMemoUseCase(state.contentId)
                            ContentType.CALENDAR -> deleteScheduleUseCase(state.contentId)
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
        }
    }
} 