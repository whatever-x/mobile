package com.whatever.caramel.feature.content.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.feature.content.create.mvi.ContentCreateIntent
import com.whatever.caramel.feature.content.create.mvi.ContentCreateSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ContentCreateRoute(
    viewModel: ContentCreateViewModel = koinViewModel(),
    navigateToBackStack: () -> Unit,
    showErrorDialog: (String, String?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ContentCreateSideEffect.NavigateToBackStack -> navigateToBackStack()
                is ContentCreateSideEffect.ShowErrorSnackBar -> {
                    showSnackbarMessage(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = this,
                        message = sideEffect.message ?: ""
                    )
                }
                is ContentCreateSideEffect.ShowToast -> {
                    showSnackbarMessage(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = this,
                        message = sideEffect.message
                    )
                }

                is ContentCreateSideEffect.ShowErrorDialog -> showErrorDialog(sideEffect.message, sideEffect.description)
            }
        }
    }

    ContentScreen(
        state = state,
        onIntent = viewModel::intent
    )
    CaramelDialog(
        show = state.showEditConfirmDialog,
        title = "작성한 내용이 저장되지 않아요.\n" +
                "그대로 나가시겠어요?",
        mainButtonText = "유지하기",
        subButtonText = "나가기",
        onDismissRequest = { viewModel.intent(ContentCreateIntent.ClickEditDialogRightButton) },
        onMainButtonClick = { viewModel.intent(ContentCreateIntent.ClickEditDialogRightButton) },
        onSubButtonClick = { viewModel.intent(ContentCreateIntent.ClickEditDialogLeftButton) }
    ) {
        DefaultCaramelDialogLayout()
    }
}