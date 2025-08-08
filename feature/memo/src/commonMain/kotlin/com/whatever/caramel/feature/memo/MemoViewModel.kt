package com.whatever.caramel.feature.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.content.GetAllTagsUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoListUseCase
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.memo.model.TagUiModel
import com.whatever.caramel.feature.memo.model.toUiModel
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoSideEffect
import com.whatever.caramel.feature.memo.mvi.MemoState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class MemoViewModel(
    private val getMemoListUseCase: GetMemoListUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<MemoState, MemoSideEffect, MemoIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): MemoState = MemoState()

    override suspend fun handleIntent(intent: MemoIntent) {
        when (intent) {
            is MemoIntent.ClickMemo -> clickMemo(intent)
            is MemoIntent.ClickTagChip -> clickTagChip(intent)
            MemoIntent.PullToRefresh -> refreshMemos()
            MemoIntent.ReachedEndOfList -> loadPagingData()
            MemoIntent.Initialize -> initialize()
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        reduce {
            copy(
                isMemoLoading = false,
                isRefreshing = false,
                isTagLoading = false,
            )
        }
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        MemoSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )
                ErrorUiType.DIALOG ->
                    postSideEffect(
                        MemoSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                MemoSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
        }
    }

    private fun initialize() {
        launch {
            reduce {
                copy(
                    isMemoLoading = true,
                    isTagLoading = true,
                    isRefreshing = false,
                    memos = persistentListOf(),
                    tags = persistentListOf(),
                    selectedTag = null,
                    selectedChipIndex = 0,
                    cursor = null,
                )
            }
            getMemos()
            getTags()
        }
    }

    private fun getTags() {
        reduce { copy(isTagLoading = true) }
        launch {
            val tags = getAllTagsUseCase().map { TagUiModel.toUiModel(it) }
            val combinedTags = listOf(TagUiModel()) + tags
            reduce {
                copy(
                    isTagLoading = false,
                    tags = combinedTags.toImmutableList(),
                    selectedTag = combinedTags.first(),
                )
            }
        }
    }

    private fun clickMemo(intent: MemoIntent.ClickMemo) {
        postSideEffect(MemoSideEffect.NavigateToMemoDetail(intent.memoId, ContentType.MEMO))
    }

    private fun loadPagingData() {
        if (currentState.isMemoLoading) return
        if (currentState.cursor == null && currentState.memos.isNotEmpty()) return
        getMemos()
    }

    private fun clickTagChip(intent: MemoIntent.ClickTagChip) {
        if (currentState.selectedTag == intent.tag) return

        reduce {
            copy(
                isMemoLoading = true,
                selectedTag = intent.tag,
                cursor = null,
                memos = persistentListOf(),
                selectedChipIndex = intent.index,
            )
        }
        getMemos()
    }

    private fun refreshMemos() {
        reduce {
            copy(
                isRefreshing = true,
                isMemoLoading = true,
                cursor = null,
                memos = persistentListOf(),
            )
        }
        getMemos()
    }

    private fun getMemos() {
        launch {
            val newMemos =
                getMemoListUseCase(
                    size = 10,
                    cursor = currentState.cursor,
                    tagId = currentState.selectedTag?.id,
                )
            val updatedMemos =
                if (newMemos.memos.isEmpty()) {
                    currentState.memos
                } else {
                    currentState.memos + newMemos.memos.map { it.toUiModel() }
                }
            reduce {
                copy(
                    isMemoLoading = false,
                    isRefreshing = false,
                    cursor = newMemos.nextCursor,
                    memos = updatedMemos.toImmutableList(),
                )
            }
        }
    }
}
