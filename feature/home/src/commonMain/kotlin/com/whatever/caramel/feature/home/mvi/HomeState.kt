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
    val balanceGameQuestion: String = "",
    val balanceGameCardState: BalanceGameCardState = BalanceGameCardState.IDLE,
    val balanceGameAnswerState: BalanceGameAnswerState = BalanceGameAnswerState.IDLE,
    val balanceGameOptions: ImmutableList<BalanceGameOption> = persistentListOf(),
    val myChoiceOption: BalanceGameOption = BalanceGameOption(),
    val partnerChoice: BalanceGameOption = BalanceGameOption(),
) : UiState {

    val isSetAnniversary: Boolean
        get() = daysTogether != 0

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
