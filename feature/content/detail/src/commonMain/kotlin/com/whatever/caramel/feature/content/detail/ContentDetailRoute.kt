package com.whatever.caramel.feature.content.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailIntent
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ContentDetailRoute(
    viewModel: ContentDetailViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    navigateToEdit: (contentId: Long, type: ContentType) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ContentDetailSideEffect.NavigateToBackStack -> popBackStack()
                is ContentDetailSideEffect.NavigateToEdit -> navigateToEdit(
                    sideEffect.contentId,
                    sideEffect.type
                )

                is ContentDetailSideEffect.ShowErrorSnackBar -> {
                    showSnackbarMessage(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = this,
                        message = sideEffect.message ?: ""
                    )
                }
            }
        }
    }

    ContentDetailScreen(
        state = state,
        onIntent = viewModel::intent
    )

    if (state.showDeleteConfirmDialog) {
        CaramelDialog(
            show = state.showDeleteConfirmDialog,
            title = "정말로 삭제하시겠어요?\n삭제한 내용은 복구할 수 없어요.",
            mainButtonText = "삭제하기",
            subButtonText = "유지하기",
            onDismissRequest = { viewModel.intent(ContentDetailIntent.ClickCancelDeleteDialogButton) },
            onMainButtonClick = { viewModel.intent(ContentDetailIntent.ClickConfirmDeleteDialogButton) },
            onSubButtonClick = { viewModel.intent(ContentDetailIntent.ClickCancelDeleteDialogButton) }
        ) {
            DefaultCaramelDialogLayout()
        }
    }
} 