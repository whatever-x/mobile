package com.whatever.caramel.feature.content.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.feature.content.edit.mvi.ContentEditIntent
import com.whatever.caramel.feature.content.edit.mvi.ContentEditSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContentEditRoute(
    viewModel: ContentEditViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    showErrorDialog: (String, String?) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ContentEditSideEffect.NavigateBack -> popBackStack()
                is ContentEditSideEffect.NavigateBackToContentList -> {
                    popBackStack()
                    popBackStack()
                }
                is ContentEditSideEffect.ShowErrorSnackBar -> {
                    showSnackbarMessage(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = this,
                        message = sideEffect.message,
                    )
                }

                is ContentEditSideEffect.ShowErrorDialog -> showErrorDialog(sideEffect.message, sideEffect.description)
            }
        }
    }

    ContentEditScreen(
        state = state,
        onIntent = viewModel::intent,
    )

    CaramelDialog(
        show = state.showExitConfirmDialog,
        title = "작성한 내용이 저장되지 않아요.\n그대로 나가시겠어요?",
        mainButtonText = "나가기",
        subButtonText = "머무르기",
        onDismissRequest = { viewModel.intent(ContentEditIntent.DismissExitDialog) },
        onMainButtonClick = { viewModel.intent(ContentEditIntent.ConfirmExitDialog) },
        onSubButtonClick = { viewModel.intent(ContentEditIntent.DismissExitDialog) },
    ) {
        DefaultCaramelDialogLayout() // Or your custom layout
    }

    val itemType = if (state.type == ContentType.MEMO) "메모를" else "일정을"
    CaramelDialog(
        show = state.showDeleteConfirmDialog,
        title = "이 $itemType 삭제하시겠어요?",
        mainButtonText = "삭제하기",
        subButtonText = "유지하기",
        onDismissRequest = { viewModel.intent(ContentEditIntent.DismissDeleteDialog) },
        onMainButtonClick = { viewModel.intent(ContentEditIntent.ConfirmDeleteDialog) },
        onSubButtonClick = { viewModel.intent(ContentEditIntent.DismissDeleteDialog) },
    ) {
        DefaultCaramelDialogLayout()
    }

    CaramelDialog(
        show = state.showDeletedContentDialog,
        title = "삭제된 메모에요",
        mainButtonText = "확인",
        onDismissRequest = { viewModel.intent(ContentEditIntent.DismissDeletedContentDialog) },
        onMainButtonClick = { viewModel.intent(ContentEditIntent.DismissDeletedContentDialog) },
    ) {
        DefaultCaramelDialogLayout()
    }
}
