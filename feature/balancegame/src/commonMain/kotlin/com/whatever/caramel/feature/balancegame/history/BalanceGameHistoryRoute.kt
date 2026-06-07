package com.whatever.caramel.feature.balancegame.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.balancegame.history.mvi.BalanceGameHistorySideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BalanceGameHistoryRoute(
    viewModel: BalanceGameHistoryViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
    navigateToShare: (
        question: String,
        myChoice: String,
        partnerChoice: String,
        myGender: Gender,
        partnerGender: Gender,
        isSameChoice: Boolean,
    ) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is BalanceGameHistorySideEffect.NavigateToBack -> navigateToBack()
                is BalanceGameHistorySideEffect.NavigateToShare ->
                    navigateToShare(
                        sideEffect.question,
                        sideEffect.myChoice,
                        sideEffect.partnerChoice,
                        sideEffect.myGender.toGender(),
                        sideEffect.partnerGender.toGender(),
                        sideEffect.myChoice == sideEffect.partnerChoice,
                    )
            }
        }
    }

    BalanceGameHistoryScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}

private fun String.toGender(): Gender = runCatching { Gender.valueOf(this) }.getOrDefault(Gender.IDLE)
