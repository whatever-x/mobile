package com.whatever.caramel.feature.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.content.GetAllTagsUseCase
import com.whatever.caramel.core.domain.usecase.memo.GetMemoListUseCase
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.memo.mvi.MemoContentState
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
            is MemoIntent.PullToRefresh -> initialize()
            is MemoIntent.ReachedEndOfList -> loadPagingData()
            is MemoIntent.Initialize -> initialize()
            is MemoIntent.ClickRecommendMemo -> clickRecommendMemo(intent)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        reduce {
            copy(
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

    private fun clickRecommendMemo(intent: MemoIntent.ClickRecommendMemo) {
        postSideEffect(
            MemoSideEffect.NavigateToCreateMemoWithTitle(
                title = intent.title,
                contentType = ContentType.MEMO
            ),
        )
    }

    private suspend fun initialize() {
        initMemoList()
        initTagList()
    }

    private suspend fun initTagList() {
        val tags = getAllTagsUseCase()
        val combinedTags = currentState.tagList + tags

        reduce {
            copy(
                isTagLoading = false,
                tagList = combinedTags.toImmutableList()
            )
        }
    }

    private fun clickMemo(intent: MemoIntent.ClickMemo) {
        postSideEffect(MemoSideEffect.NavigateToMemoDetail(intent.memoId, ContentType.MEMO))
    }

    private fun loadPagingData() {
        val currentMemoContentState = currentState.memoContent

        when(currentMemoContentState) {
            is MemoContentState.Empty,
            is MemoContentState.Loading -> return
            is MemoContentState.Content -> {
                launch {
                    val newPagingData = getMemoListUseCase(
                        size = 10,
                        cursor = currentState.cursor,
                        tagId = currentState.selectedTag?.id,
                    )

                    if (newPagingData.memos.isNotEmpty()) {
                        reduce {
                            copy(
                                cursor = newPagingData.nextCursor,
                                memoContent = currentMemoContentState.copy(
                                    memoList = (currentMemoContentState.memoList.toSet() + newPagingData.memos).toImmutableList()
                                )
                            )
                        }
                    } else {
                        reduce { copy(cursor = newPagingData.nextCursor) }
                    }
                }
            }
        }
    }

    private fun clickTagChip(intent: MemoIntent.ClickTagChip) {
        if (currentState.selectedTag == intent.tag) return

        reduce {
            copy(
                selectedTag = intent.tag,
                cursor = null,
                memoContent = MemoContentState.Loading,
            )
        }

        initMemoList()
    }

    private suspend fun initMemoList() {
        val memoWIthCursor =
            getMemoListUseCase(
                size = 10,
                cursor = currentState.cursor,
                tagId = currentState.selectedTag?.id,
            )

        val memoContentState =
            if (memoWIthCursor.memos.isEmpty()) {
                MemoContentState.Empty
            } else {
                MemoContentState.Content(memoList = memoWIthCursor.memos.toImmutableList())
            }

        reduce {
            copy(
                cursor = memoWIthCursor.nextCursor,
                memoContent = memoContentState
            )
        }
    }
}
