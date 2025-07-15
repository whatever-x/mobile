package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
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
import com.whatever.caramel.feature.home.mvi.HomeSideEffect.*
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(savedStateHandle) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): HomeState = HomeState()

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickAnniversaryNudgeCard -> postSideEffect(HomeSideEffect.NavigateToEditAnniversary)
            is HomeIntent.ClickSettingButton -> postSideEffect(HomeSideEffect.NavigateToSetting)
            is HomeIntent.ClickTodoContent -> {
                postSideEffect(
                    NavigateToContentDetail(
                        contentId = intent.todoContentId,
                        contentType = ContentType.CALENDAR,
                    ),
                )
            }
            is HomeIntent.CreateTodoContent -> postSideEffect(HomeSideEffect.NavigateToCreateContent)
            is HomeIntent.SaveShareMessage -> saveShareMessage()
            is HomeIntent.ShowShareMessageEditBottomSheet -> showBottomSheet()
            is HomeIntent.HideShareMessageEditBottomSheet -> hideBottomSheet()
            is HomeIntent.PullToRefresh -> refreshHomeData()
            is HomeIntent.ClickBalanceGameOptionButton -> submitBalanceGameOption(balanceGameOptionState = intent.option)
            is HomeIntent.ChangeBalanceGameCardState -> changeBalanceGameCardState()
            is HomeIntent.LoadDataOnStart -> loadDataOnStart()
            is HomeIntent.HideDialog -> hideDialog()
            is HomeIntent.ClearShareMessage -> clearShareMessage()
            is HomeIntent.InputShareMessage -> inputShareMessage(text = intent.newShareMessage)
        }
    }

    private fun inputShareMessage(text: String) {
        if (!text.contains("\n") && Regex("\\X").findAll(text).count() <= 24) {
            reduce {
                copy(
                    bottomSheetShareMessage = text
                )
            }
        }
    }

    private fun clearShareMessage() {
        reduce {
            copy(
                bottomSheetShareMessage = ""
            )
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)

        val exception = throwable as CaramelException

        when (exception.code) {
            CoupleErrorCode.CAN_NOT_LOAD_DATA -> {
                reduce {
                    copy(
                        isShowBottomSheet = false,
                        isShowDialog = true,
                        dialogTitle = exception.message,
                        coupleState = HomeState.CoupleState.DISCONNECT,
                    )
                }
            }
            CoupleErrorCode.MEMBER_NOT_FOUND -> {
                if (currentState.coupleState != HomeState.CoupleState.DISCONNECT) {
                    reduce {
                        copy(
                            isShowDialog = true,
                            dialogTitle = exception.message,
                            coupleState = HomeState.CoupleState.DISCONNECT,
                        )
                    }
                }
            }
            else -> {
                when (exception.errorUiType) {
                    ErrorUiType.TOAST ->
                        postSideEffect(
                            HomeSideEffect.ShowErrorToast(
                                message = throwable.message,
                            ),
                        )
                    ErrorUiType.DIALOG ->
                        postSideEffect(
                            HomeSideEffect.ShowErrorDialog(
                                message = throwable.message,
                                description = throwable.description,
                            ),
                        )
                }
            }
        }
    }

    private fun hideDialog() {
        reduce {
            copy(
                isShowDialog = false,
                dialogTitle = "",
            )
        }
    }

    private suspend fun loadDataOnStart() {
        launch {
            val initCoupleInfoJob = launch { initCoupleInfo() }
            val initSchedulesJob = launch { initSchedules() }
            val initBalanceGameJob = launch { initBalanceGame() }

            joinAll(initCoupleInfoJob, initSchedulesJob, initBalanceGameJob)
        }
    }

    private suspend fun saveShareMessage() {
        launch {
            updateShareMessageUseCase(shareMessage = currentState.bottomSheetShareMessage)
            postSideEffect(HomeSideEffect.HideKeyboard)

            reduce {
                copy(
                    shareMessage = currentState.bottomSheetShareMessage,
                    isShowBottomSheet = false,
                )
            }
        }
    }

    private suspend fun refreshHomeData() {
        launch {
            reduce {
                copy(
                    isLoading = true,
                    coupleState = HomeState.CoupleState.IDLE,
                )
            }

            val initCoupleInfoJob = launch { initCoupleInfo() }
            val initSchedulesJob = launch { initSchedules() }
            val initBalanceGameJob = launch { initBalanceGame() }

            joinAll(initCoupleInfoJob, initSchedulesJob, initBalanceGameJob)

            reduce { copy(isLoading = false) }
        }
    }

    private fun showBottomSheet() {
        reduce {
            copy(
                bottomSheetShareMessage = currentState.shareMessage,
                isShowBottomSheet = true
            )
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
                coupleState = HomeState.CoupleState.CONNECT,
            )
        }
    }

    private suspend fun initSchedules() {
        val schedules = getTodayScheduleUseCase()

        reduce {
            copy(
                todos =
                    if (schedules.isNotEmpty()) {
                        schedules.map { todo -> TodoState(id = todo.id, title = todo.title) }
                    } else {
                        emptyList()
                    },
            )
        }
    }

    private suspend fun initBalanceGame() {
        launch {
            val todayBalanceGame = getTodayBalanceGameUseCase()

            reduce {
                copy(
                    balanceGameState =
                        BalanceGameState(
                            id = todayBalanceGame.gameInfo.id,
                            question = todayBalanceGame.gameInfo.question,
                            options =
                                todayBalanceGame.gameInfo.options
                                    .map {
                                        BalanceGameOptionState(
                                            id = it.optionId,
                                            name = it.text,
                                        )
                                    }.toImmutableList(),
                        ),
                    myChoiceOption =
                        BalanceGameOptionState(
                            id = todayBalanceGame.myChoice?.optionId ?: 0L,
                            name = todayBalanceGame.myChoice?.text ?: "",
                        ),
                    partnerChoiceOption =
                        BalanceGameOptionState(
                            id = todayBalanceGame.partnerChoice?.optionId ?: 0L,
                            name = todayBalanceGame.partnerChoice?.text ?: "",
                        ),
                )
            }
        }
    }

    private fun submitBalanceGameOption(balanceGameOptionState: BalanceGameOptionState) {
        launch {
            val result =
                submitBalanceGameChoiceUseCase(
                    gameId = currentState.balanceGameState.id,
                    optionId = balanceGameOptionState.id,
                )

            reduce {
                copy(
                    myChoiceOption =
                        BalanceGameOptionState(
                            id = result.myChoice?.optionId ?: 0L,
                            name = result.myChoice?.text ?: "",
                        ),
                    partnerChoiceOption =
                        BalanceGameOptionState(
                            id = result.partnerChoice?.optionId ?: 0L,
                            name = result.partnerChoice?.text ?: "",
                        ),
                )
            }
        }
    }

    private fun changeBalanceGameCardState() {
        reduce {
            copy(balanceGameCardState = HomeState.BalanceGameCardState.CONFIRM)
        }
    }
}
