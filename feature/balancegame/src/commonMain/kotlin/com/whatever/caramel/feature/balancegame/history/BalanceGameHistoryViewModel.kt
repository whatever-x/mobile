package com.whatever.caramel.feature.balancegame.history

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.usecase.balanceGame.GetBalanceGameHistoryUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleRelationshipInfoUseCase
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.balancegame.history.model.toHistoryUiModel
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryIntent
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistorySideEffect
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistoryState
import kotlinx.collections.immutable.toImmutableList

class BalanceGameHistoryViewModel(
    private val getBalanceGameHistoryUseCase: GetBalanceGameHistoryUseCase,
    private val getCoupleRelationshipInfoUseCase: GetCoupleRelationshipInfoUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<BalanceGameHistoryState, BalanceGameHistorySideEffect, BalanceGameHistoryIntent>(savedStateHandle, crashlytics) {
    init {
        loadInitialData()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): BalanceGameHistoryState = BalanceGameHistoryState()

    override suspend fun handleIntent(intent: BalanceGameHistoryIntent) {
        when (intent) {
            is BalanceGameHistoryIntent.ClickBackButton -> postSideEffect(BalanceGameHistorySideEffect.NavigateToBack)
            is BalanceGameHistoryIntent.ClickHistoryCard -> toggleCard(gameId = intent.gameId)
            is BalanceGameHistoryIntent.ClickShareResult -> shareResult(gameId = intent.gameId)
            is BalanceGameHistoryIntent.LoadMore -> loadMore()
        }
    }

    private fun loadInitialData() {
        launch {
            reduce { copy(isLoading = true) }
            val coupleJob =
                launch {
                    val couple = getCoupleRelationshipInfoUseCase()
                    reduce {
                        copy(
                            myNickname = couple.myInfo.userProfile.nickName,
                            myGender = couple.myInfo.userProfile.gender,
                            partnerNickname = couple.partnerInfo?.userProfile?.nickName ?: "",
                            partnerGender = couple.partnerInfo?.userProfile?.gender ?: Gender.IDLE,
                        )
                    }
                }
            val historyJob =
                launch {
                    val history = getBalanceGameHistoryUseCase(cursor = null)
                    reduce {
                        copy(
                            items = history.gameResults.map { it.toHistoryUiModel() }.toImmutableList(),
                            nextCursor = history.nextCursor,
                            isEndReached = history.nextCursor == null,
                        )
                    }
                }
            coupleJob.join()
            historyJob.join()
            reduce { copy(isLoading = false) }
        }
    }

    private fun loadMore() {
        if (currentState.isLoadingMore || currentState.isEndReached || currentState.nextCursor == null) return
        launch {
            reduce { copy(isLoadingMore = true) }
            val history = getBalanceGameHistoryUseCase(cursor = currentState.nextCursor)
            reduce {
                copy(
                    items = (items + history.gameResults.map { it.toHistoryUiModel() }).toImmutableList(),
                    nextCursor = history.nextCursor,
                    isEndReached = history.nextCursor == null,
                    isLoadingMore = false,
                )
            }
        }
    }

    private fun toggleCard(gameId: Long) {
        reduce {
            copy(expandedGameId = if (expandedGameId == gameId) null else gameId)
        }
    }

    private fun shareResult(gameId: Long) {
        val item = currentState.items.firstOrNull { it.gameId == gameId } ?: return
        postSideEffect(
            BalanceGameHistorySideEffect.NavigateToShare(
                gameId = item.gameId,
                question = item.question,
                myNickname = currentState.myNickname,
                myGender = currentState.myGender.name,
                myChoice = item.myChoiceText,
                partnerNickname = currentState.partnerNickname,
                partnerGender = currentState.partnerGender.name,
                partnerChoice = item.partnerChoiceText,
            ),
        )
    }
}
