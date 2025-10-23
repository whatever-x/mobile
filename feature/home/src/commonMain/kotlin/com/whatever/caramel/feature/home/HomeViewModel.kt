package com.whatever.caramel.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.BalanceGameErrorCode
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.usecase.app.AddAppLaunchCountUseCase
import com.whatever.caramel.core.domain.usecase.app.CheckInAppReviewAvailableUseCase
import com.whatever.caramel.core.domain.usecase.balanceGame.GetTodayBalanceGameUseCase
import com.whatever.caramel.core.domain.usecase.balanceGame.SubmitBalanceGameChoiceUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleRelationshipInfoUseCase
import com.whatever.caramel.core.domain.usecase.couple.UpdateShareMessageUseCase
import com.whatever.caramel.core.domain.usecase.schedule.GetTodayScheduleUseCase
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.util.codePointCount
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.home.mvi.BalanceGameCard
import com.whatever.caramel.feature.home.mvi.BalanceGameOptionItem
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import com.whatever.caramel.feature.home.mvi.HomeSideEffect.NavigateToContentDetail
import com.whatever.caramel.feature.home.mvi.HomeState
import com.whatever.caramel.feature.home.mvi.HomeState.Companion.MAX_SHARE_MESSAGE_LENGTH
import com.whatever.caramel.feature.home.mvi.ScheduleItem
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class HomeViewModel(
    private val updateShareMessageUseCase: UpdateShareMessageUseCase,
    private val getCoupleRelationshipInfoUseCase: GetCoupleRelationshipInfoUseCase,
    private val getTodayScheduleUseCase: GetTodayScheduleUseCase,
    private val getTodayBalanceGameUseCase: GetTodayBalanceGameUseCase,
    private val submitBalanceGameChoiceUseCase: SubmitBalanceGameChoiceUseCase,
    private val addAppLaunchCountUseCase: AddAppLaunchCountUseCase,
    private val checkInAppReviewAvailableUseCase: CheckInAppReviewAvailableUseCase,
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(savedStateHandle, crashlytics) {
    init {
        viewModelScope.launch {
            addAppLaunchCountUseCase()
            checkInAppReviewAvailableUseCase().collect { isAvailable ->
                if (isAvailable) postSideEffect(HomeSideEffect.RequestInAppReview)
            }
        }
    }

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
            is HomeIntent.ClickBalanceGameOptionButton -> clickBalanceGameOption(balanceGameOption = intent.option)
            is HomeIntent.LoadDataOnStart -> loadDataOnStart()
            is HomeIntent.HideDialog -> hideDialog()
            is HomeIntent.ClearShareMessage -> clearShareMessage()
            is HomeIntent.InputShareMessage -> inputShareMessage(text = intent.newShareMessage)
            is HomeIntent.RotateBalanceGameCard -> rotate()
        }
    }

    private suspend fun rotate() {
        reduce {
            copy(isBalanceGameCardRotated = true)
        }
    }

    private fun inputShareMessage(text: String) {
        if (text.codePointCount() > MAX_SHARE_MESSAGE_LENGTH || text.contains("\n")) {
            return
        } else {
            reduce {
                copy(
                    bottomSheetShareMessage = text,
                )
            }
        }
    }

    private fun clearShareMessage() {
        reduce {
            copy(
                bottomSheetShareMessage = "",
            )
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.code) {
                BalanceGameErrorCode.CAN_NOT_CHANGE_OPTION -> {
                    launch { initBalanceGame() }
                }

                CoupleErrorCode.CAN_NOT_LOAD_DATA -> {
                    reduce {
                        copy(
                            isShowBottomSheet = false,
                            isShowDialog = true,
                            dialogTitle = throwable.message,
                            coupleState = HomeState.CoupleState.DISCONNECT,
                        )
                    }
                }

                CoupleErrorCode.MEMBER_NOT_FOUND -> {
                    if (currentState.coupleState != HomeState.CoupleState.DISCONNECT) {
                        reduce {
                            copy(
                                isShowDialog = true,
                                dialogTitle = throwable.message,
                                coupleState = HomeState.CoupleState.DISCONNECT,
                            )
                        }
                    }
                }

                else -> {
                    when (throwable.errorUiType) {
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
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                HomeSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
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
        val initCoupleInfoJob = launch { initCoupleInfo() }
        val initSchedulesJob = launch { initSchedules() }
        val initBalanceGameJob = launch { initBalanceGame() }

        joinAll(initCoupleInfoJob, initSchedulesJob, initBalanceGameJob)
    }

    private suspend fun saveShareMessage() {
        updateShareMessageUseCase(shareMessage = currentState.bottomSheetShareMessage)
        postSideEffect(HomeSideEffect.HideKeyboard)

        reduce {
            copy(
                shareMessage = currentState.bottomSheetShareMessage,
                isShowBottomSheet = false,
            )
        }
    }

    private suspend fun refreshHomeData() {
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

    private fun showBottomSheet() {
        reduce {
            copy(
                bottomSheetShareMessage = currentState.shareMessage,
                isShowBottomSheet = true,
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
                myNickname = coupleRelationShip.myInfo.userProfile.nickName,
                partnerNickname = coupleRelationShip.partnerInfo?.userProfile?.nickName ?: "",
                myGender = coupleRelationShip.myInfo.userProfile.gender,
                partnerGender = coupleRelationShip.partnerInfo?.userProfile?.gender ?: Gender.IDLE,
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
                todoList =
                    if (schedules.isNotEmpty()) {
                        schedules
                            .map { schedule ->
                                ScheduleItem(
                                    id = schedule.id,
                                    title = schedule.contentData.title.ifEmpty { schedule.contentData.description },
                                    contentAssignee = schedule.contentData.contentAssignee,
                                )
                            }.toImmutableList()
                    } else {
                        persistentListOf()
                    },
            )
        }
    }

    private suspend fun initBalanceGame() {
        val todayBalanceGame = getTodayBalanceGameUseCase()

        reduce {
            copy(
                balanceGameCard =
                    BalanceGameCard(
                        id = todayBalanceGame.gameInfo.id,
                        question = todayBalanceGame.gameInfo.question,
                        options =
                            todayBalanceGame.gameInfo.options
                                .map {
                                    BalanceGameOptionItem(
                                        id = it.optionId,
                                        name = it.text,
                                    )
                                }.toImmutableList(),
                        myOption =
                            todayBalanceGame.myChoice?.let {
                                BalanceGameOptionItem(
                                    id = it.optionId,
                                    name = it.text,
                                )
                            },
                        partnerOption =
                            todayBalanceGame.partnerChoice?.let {
                                BalanceGameOptionItem(
                                    id = it.optionId,
                                    name = it.text,
                                )
                            },
                    ),
            )
        }
    }

    private suspend fun clickBalanceGameOption(balanceGameOption: BalanceGameOptionItem) {
        val result =
            submitBalanceGameChoiceUseCase(
                gameId = currentState.balanceGameCard.id,
                optionId = balanceGameOption.id,
            )

        reduce {
            copy(
                balanceGameCard =
                    currentState.balanceGameCard.copy(
                        myOption =
                            result.myChoice?.let {
                                BalanceGameOptionItem(
                                    id = it.optionId,
                                    name = it.text,
                                )
                            },
                        partnerOption =
                            result.partnerChoice?.let {
                                BalanceGameOptionItem(
                                    id = it.optionId,
                                    name = it.text,
                                )
                            },
                    ),
            )
        }
    }
}
