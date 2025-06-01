package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.balanceGame.GetTodayBalanceGameUseCase
import com.whatever.caramel.core.domain.usecase.balanceGame.SubmitBalanceGameChoiceUseCase
import com.whatever.caramel.core.domain.usecase.calendar.GetTodayScheduleUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleRelationshipInfoUseCase
import com.whatever.caramel.core.domain.usecase.couple.UpdateShareMessageUseCase
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.home.mvi.BalanceGameOptionState
import com.whatever.caramel.feature.home.mvi.BalanceGameState
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import com.whatever.caramel.feature.home.mvi.HomeState
import com.whatever.caramel.feature.home.mvi.TodoState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.joinAll

class HomeViewModel(
    private val updateShareMessageUseCase: UpdateShareMessageUseCase,
    private val getCoupleRelationshipInfoUseCase: GetCoupleRelationshipInfoUseCase,
    private val getTodayScheduleUseCase: GetTodayScheduleUseCase,
    private val getTodayBalanceGameUseCase: GetTodayBalanceGameUseCase,
    private val submitBalanceGameChoiceUseCase: SubmitBalanceGameChoiceUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(savedStateHandle) {

    init {
        launch {
            initCoupleInfo()
            initSchedules()
            initBalanceGame()
        }
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): HomeState {
        return HomeState()
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickAnniversaryNudgeCard -> postSideEffect(HomeSideEffect.NavigateToEditAnniversary)
            is HomeIntent.ClickSettingButton -> postSideEffect(HomeSideEffect.NavigateToSetting)
            is HomeIntent.ClickTodoContent -> {
                postSideEffect(
                    HomeSideEffect.NavigateToContentDetail(
                        contentId = intent.todoContentId,
                        contentType = ContentType.CALENDAR
                    )
                )
            }

            is HomeIntent.CreateTodoContent -> postSideEffect(HomeSideEffect.NavigateToCreateContent)
            is HomeIntent.SaveShareMessage -> saveShareMessage(newShareMessage = intent.newShareMessage)
            is HomeIntent.ShowShareMessageEditBottomSheet -> showBottomSheet()
            is HomeIntent.HideShareMessageEditBottomSheet -> hideBottomSheet()
            is HomeIntent.PullToRefresh -> refreshHomeData()
            is HomeIntent.ClickBalanceGameOptionButton -> submitBalanceGameOption(
                balanceGameOptionState = intent.option
            )

            is HomeIntent.ClickBalanceGameResultButton -> checkBalanceGameResult()
            is HomeIntent.ChangeBalanceGameCardState -> changeBalanceGameCardState()
        }
    }

    private suspend fun saveShareMessage(newShareMessage: String) {
        launch {
            val updatedMessage = updateShareMessageUseCase(shareMessage = newShareMessage)

            reduce {
                copy(
                    shareMessage = updatedMessage,
                    isShowBottomSheet = false
                )
            }
        }
    }

    private suspend fun refreshHomeData() {
        launch {
            reduce { copy(isLoading = true) }

            val initCoupleInfoJob = launch { initCoupleInfo() }
            val initSchedulesJob = launch { initSchedules() }
            val initBalanceGameJob = launch { initBalanceGame() }

            joinAll(initCoupleInfoJob, initSchedulesJob, initBalanceGameJob)

            reduce {
                copy(
                    isBalanceGameCardRotated = false,
                    isLoading = false
                )
            }
        }
    }

    private fun showBottomSheet() {
        reduce {
            copy(isShowBottomSheet = true)
        }
    }

    private fun hideBottomSheet() {
        reduce {
            copy(isShowBottomSheet = false)
        }
    }

    private suspend fun initCoupleInfo() {
        val coupleRelationShip = getCoupleRelationshipInfoUseCase()

        reduce {
            copy(
                myNickname = coupleRelationShip.myInfo.userProfile?.nickName ?: "",
                partnerNickname = coupleRelationShip.partnerInfo.userProfile?.nickName ?: "",
                myGender = coupleRelationShip.myInfo.userProfile?.gender ?: Gender.IDLE,
                partnerGender = coupleRelationShip.partnerInfo.userProfile?.gender ?: Gender.IDLE,
                daysTogether = coupleRelationShip.info.daysTogether,
                shareMessage = coupleRelationShip.info.sharedMessage,
            )
        }
    }

    private suspend fun initSchedules() {
        val schedules = getTodayScheduleUseCase()

        if (schedules.isNotEmpty()) {
            val todoUiState = schedules.map { todo ->
                TodoState(
                    id = todo.id,
                    title = todo.title,
                )
            }

            reduce {
                copy(
                    todos = todoUiState
                )
            }
        }
    }

    private suspend fun initBalanceGame() {
        launch {
            val todayBalanceGame = getTodayBalanceGameUseCase()

            reduce {
                copy(
                    balanceGameState = BalanceGameState(
                        id = todayBalanceGame.gameInfo.id,
                        question = todayBalanceGame.gameInfo.question,
                        options = todayBalanceGame.gameInfo.options.map {
                            BalanceGameOptionState(
                                id = it.optionId,
                                name = it.text
                            )
                        }.toImmutableList()
                    ),
                    balanceGameCardState = HomeState.BalanceGameCardState.IDLE,
                    myChoiceOption =
                        BalanceGameOptionState(
                            id = todayBalanceGame.myChoice?.optionId ?: 0L,
                            name = todayBalanceGame.myChoice?.text ?: ""
                        ),
                    partnerChoiceOption =
                        BalanceGameOptionState(
                            id = todayBalanceGame.partnerChoice?.optionId ?: 0L,
                            name = todayBalanceGame.partnerChoice?.text ?: ""
                        ),
                )
            }
        }
    }

    private fun submitBalanceGameOption(balanceGameOptionState: BalanceGameOptionState) {
        launch {
            val result = submitBalanceGameChoiceUseCase(
                gameId = currentState.balanceGameState.id,
                optionId = balanceGameOptionState.id
            )

            reduce {
                copy(
                    myChoiceOption =
                        BalanceGameOptionState(
                            id = result.myChoice?.optionId ?: 0L,
                            name = result.myChoice?.text ?: ""
                        ),
                    partnerChoiceOption =
                        BalanceGameOptionState(
                            id = result.partnerChoice?.optionId ?: 0L,
                            name = result.partnerChoice?.text ?: ""
                        ),
                )
            }
        }
    }

    private fun checkBalanceGameResult() {
        reduce {
            copy(
                isBalanceGameCardRotated = !isBalanceGameCardRotated
            )
        }
    }

    private fun changeBalanceGameCardState() {
        reduce {
            copy(balanceGameCardState = HomeState.BalanceGameCardState.CONFIRM)
        }
    }

}