package com.whatever.caramel.feature.home.mvi

import com.whatever.caramel.core.domain.validator.util.codePointCount
import com.whatever.caramel.core.domain.vo.content.ContentAssignee
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val myNickname: String = "",
    val myGender: Gender = Gender.IDLE,
    val partnerNickname: String = "",
    val partnerGender: Gender = Gender.IDLE,
    val daysTogether: Int = 0,
    val shareMessage: String = "",
    val todoList: ImmutableList<TodoItem> = persistentListOf(),
    val bottomSheetShareMessage: String = "",
    val isShowBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val balanceGameCard: BalanceGameCard = BalanceGameCard.initState(),
    val isBalanceGameCardRotated: Boolean = false,
    val isShowDialog: Boolean = false,
    val dialogTitle: String = "",
    val coupleState: CoupleState = CoupleState.IDLE,
) : UiState {
    val isSetAnniversary: Boolean
        get() = daysTogether != 0

    val bottomSheetShareMessageLength: Int
        get() = bottomSheetShareMessage.codePointCount()

    enum class CoupleState {
        IDLE,
        CONNECT,
        DISCONNECT,
    }

    companion object {
        const val MAX_SHARE_MESSAGE_LENGTH = 24
    }
}

data class TodoItem(
    val id: Long,
    val title: String,
    val contentAssignee: ContentAssignee,
)

data class BalanceGameCard(
    val id: Long, // 게임 ID
    val question: String, // 질문
    val options: ImmutableList<BalanceGameOptionItem>, // 옵션들
    val myOption: BalanceGameOptionItem?, // 내가 선택한 옵션
    val partnerOption: BalanceGameOptionItem?, // 상대가 선택한 옵션
) {
    val gameResult: GameResult
        get() =
            when {
                myOption == null -> GameResult.IDLE
                partnerOption == null -> GameResult.WAITING
                else -> GameResult.CHECK_RESULT
            }

    enum class GameResult {
        IDLE, // 내 선택 대기중
        WAITING, // 내 선택 완료, 상대 선택 대기중
        CHECK_RESULT, // 내 선택 완료, 상대 선택 완료
    }

    companion object {
        fun initState(): BalanceGameCard =
            BalanceGameCard(
                id = 0L,
                question = "",
                options = persistentListOf(),
                myOption = null,
                partnerOption = null,
            )
    }
}

data class BalanceGameOptionItem(
    val id: Long,
    val name: String,
)
