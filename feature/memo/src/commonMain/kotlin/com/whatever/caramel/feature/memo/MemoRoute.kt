package com.whatever.caramel.feature.memo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.util.ObserveLifecycleEvent
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MemoRoute(
    viewModel: MemoViewModel = koinViewModel(),
    navigateToTodoDetail: (Long, ContentType) -> Unit,
    showErrorToast: (String) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MemoSideEffect.NavigateToTodoDetail -> navigateToTodoDetail(sideEffect.todoId, sideEffect.contentType)
                is MemoSideEffect.ShowErrorDialog -> showErrorDialog(sideEffect.message, sideEffect.description)
                is MemoSideEffect.ShowErrorToast -> showErrorToast(sideEffect.message)
            }
        }
    }

    ObserveLifecycleEvent { event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.intent(MemoIntent.Initialize)
        }
    }

    MemoScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
