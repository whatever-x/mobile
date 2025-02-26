package com.whatever.caramel.feat.main.presentation.memo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.main.presentation.memo.mvi.MemoSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MemoRoute(
    viewModel: MemoViewModel = koinViewModel(),
    navigateToTodoDetail: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MemoSideEffect.NavigateToTodoDetail -> navigateToTodoDetail()
            }
        }
    }

    MemoScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}