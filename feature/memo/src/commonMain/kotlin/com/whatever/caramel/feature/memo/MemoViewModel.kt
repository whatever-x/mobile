package com.whatever.caramel.feature.memo

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.entity.Tag
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
import kotlinx.coroutines.joinAll

class MemoViewModel(
    private val getMemoListUseCase: GetMemoListUseCase,
    private val getAllTagsUseCase: GetAllTagsUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<MemoState, MemoSideEffect, MemoIntent>(savedStateHandle, crashlytics) {
    private var isInitialized = false

    override fun createInitialState(savedStateHandle: SavedStateHandle): MemoState = MemoState()

    override suspend fun handleIntent(intent: MemoIntent) {
        when (intent) {
            is MemoIntent.ClickMemo -> clickMemo(intent)
            is MemoIntent.ClickTagChip -> clickTagChip(intent)
            is MemoIntent.PullToRefresh -> refresh()
            is MemoIntent.Pagination -> loadPagingData()
            is MemoIntent.ClickRecommendMemo -> clickRecommendMemo(intent)
            is MemoIntent.Initialize -> initialize()
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

    private suspend fun initialize() {
        // 최초 진입에서만 로딩뷰(스켈레톤)를 노출하고, 이후 resume 에서는 로딩뷰 없이 무음 리프레시한다.
        if (isInitialized) {
            silentRefresh()
            return
        }
        isInitialized = true

        reduce {
            copy(
                isTagLoading = true,
                memoContent = MemoContentState.Loading,
                cursor = null,
                tagList = persistentListOf(Tag(id = 0L, label = "")),
                selectedTag = tagList[0],
            )
        }

        initMemoList()
        initTagList()
    }

    /**
     * resume 시 호출되는 무음 리프레시.
     * 로딩 상태(memoContent = Loading, isTagLoading = true)로 전환하지 않고,
     * 데이터를 모두 받아온 뒤 한 번에 교체해 스켈레톤 깜빡임을 없앤다.
     * 기존 [initialize] 와 동일하게 전체 탭 기준 첫 페이지로 갱신한다.
     */
    private suspend fun silentRefresh() {
        val tagListJob =
            launch {
                val fetchedTags = getAllTagsUseCase()
                val newTagList = (persistentListOf(Tag(id = 0L, label = "")) + fetchedTags).toImmutableList()

                reduce {
                    copy(
                        isTagLoading = false,
                        tagList = newTagList,
                        selectedTag = newTagList[0],
                    )
                }
            }

        val memoListJob =
            launch {
                val memoWithCursor =
                    getMemoListUseCase(
                        size = 10,
                        cursor = null,
                        tagId = 0L,
                    )

                val memoContentState =
                    if (memoWithCursor.memos.isEmpty()) {
                        MemoContentState.Empty
                    } else {
                        MemoContentState.Content(memoList = memoWithCursor.memos.toImmutableList())
                    }

                reduce {
                    copy(
                        cursor = memoWithCursor.nextCursor,
                        memoContent = memoContentState,
                    )
                }
            }

        joinAll(tagListJob, memoListJob)
    }

    private suspend fun refresh() {
        val initTagList = persistentListOf(Tag(id = 0L, label = ""))

        reduce {
            copy(
                isRefreshing = true,
                isTagLoading = true,
                memoContent = MemoContentState.Loading,
                tagList = initTagList,
                selectedTag = initTagList[0],
                cursor = null,
            )
        }

        val initTagListJob = launch { initTagList() }
        val initMemoListJob = launch { initMemoList() }

        joinAll(initTagListJob, initMemoListJob)

        reduce { copy(isRefreshing = false) }
    }

    private fun clickRecommendMemo(intent: MemoIntent.ClickRecommendMemo) {
        postSideEffect(
            MemoSideEffect.NavigateToCreateMemoWithTitle(
                title = intent.title,
                contentType = ContentType.MEMO,
            ),
        )
    }

    private suspend fun initTagList() {
        val newTagList = getAllTagsUseCase()
        val combinedTags = currentState.tagList + newTagList

        reduce {
            copy(
                isTagLoading = false,
                tagList = combinedTags.toImmutableList(),
            )
        }
    }

    private fun clickMemo(intent: MemoIntent.ClickMemo) {
        postSideEffect(MemoSideEffect.NavigateToMemoDetail(intent.memoId, ContentType.MEMO))
    }

    private suspend fun loadPagingData() {
        val currentMemoContentState = currentState.memoContent

        when (currentMemoContentState) {
            is MemoContentState.Empty,
            is MemoContentState.Loading,
            -> return

            is MemoContentState.Content -> {
                if (currentState.cursor == null) return

                val newPagingData =
                    getMemoListUseCase(
                        size = 10,
                        cursor = currentState.cursor,
                        tagId = currentState.selectedTag?.id,
                    )

                if (newPagingData.memos.isNotEmpty()) {
                    val combinedMemoList = currentMemoContentState.memoList + newPagingData.memos

                    reduce {
                        copy(
                            cursor = newPagingData.nextCursor,
                            memoContent =
                                currentMemoContentState.copy(
                                    memoList = combinedMemoList.toImmutableList(),
                                ),
                        )
                    }
                } else {
                    reduce { copy(cursor = newPagingData.nextCursor) }
                }
            }
        }
    }

    private suspend fun clickTagChip(intent: MemoIntent.ClickTagChip) {
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
                memoContent = memoContentState,
            )
        }
    }
}
