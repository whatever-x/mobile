package com.whatever.caramel.feature.home.mvi

import androidx.compose.runtime.Immutable
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
    val todos: List<TodoState> = emptyList(),
    val isShowBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val balanceGameState: BalanceGameState = BalanceGameState(),
    val balanceGameCardState: BalanceGameCardState = BalanceGameCardState.IDLE,
    val myChoiceOption: BalanceGameOptionState = BalanceGameOptionState(),
    val partnerChoiceOption: BalanceGameOptionState = BalanceGameOptionState(),
    val isShowDialog: Boolean = false,
    val dialogTitle: String = ""
    val coupleState: CoupleState = CoupleState.IDLE
) : UiState {

    val isSetAnniversary: Boolean
        get() = daysTogether != 0

    val balanceGameAnswerState: BalanceGameAnswerState
        get() = if (myChoiceOption.notSelected) { // 내가 대답하지 않은 상태
            BalanceGameAnswerState.IDLE
        } else {
            if (partnerChoiceOption.notSelected) { // 내가 대답하고 / 상대가 대답하지 않은 상태
                BalanceGameAnswerState.WAITING
            } else {
                BalanceGameAnswerState.CHECK_RESULT // 내가 대답하고 / 상대도 대답한 상태
            }
        }

    enum class BalanceGameAnswerState {
        IDLE,
        WAITING,
        CHECK_RESULT,
        ;
    }

    enum class BalanceGameCardState {
        IDLE,
        CONFIRM,
        ;
    }

    enum class CoupleState {
        IDLE,
        CONNECT,
        DISCONNECT,
        ;
    }

}

@Immutable
data class TodoState(
    val id: Long,
    val title: String,
)

data class BalanceGameState(
    val id: Long = 0L,
    val question: String = "",
    val options: ImmutableList<BalanceGameOptionState> = persistentListOf(),
)

data class BalanceGameOptionState(
    val id: Long = 0L,
    val name: String = "",
) {
    val notSelected: Boolean
        get() = id == 0L && name.isEmpty()
}
