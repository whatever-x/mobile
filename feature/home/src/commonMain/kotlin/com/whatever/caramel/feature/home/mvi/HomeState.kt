package com.whatever.caramel.feature.home.mvi

import androidx.compose.runtime.Immutable
import com.whatever.caramel.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val daysTogether: Int = 0,
    val shareMessage: String = "",
    val todos: List<TodoState> = emptyList(),
    val isSetAnniversary: Boolean = false,
    val isShowBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val myNickname: String = "",
    val partnerNickname: String = "",
    val balanceGameQuestion: String = "",
    val balanceGameCardState: BalanceGameCardState = BalanceGameCardState.IDLE,
    val balanceGameAnswerState: BalanceGameAnswerState = BalanceGameAnswerState.IDLE,
    val balanceGameOptions: ImmutableList<BalanceGameOption> = persistentListOf(),
    val myChoiceOption: BalanceGameOption = BalanceGameOption(),
    val partnerChoice: BalanceGameOption = BalanceGameOption(),
) : UiState {

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

}

@Immutable
data class TodoState(
    val id: Long,
    val title: String,
)

data class BalanceGameOption(
    val id: Int = 0,
    val name: String = "",
)
