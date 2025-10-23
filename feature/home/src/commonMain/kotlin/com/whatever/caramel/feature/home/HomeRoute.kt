package com.whatever.caramel.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.domain.vo.content.ContentType
import com.whatever.caramel.core.ui.util.ObserveLifecycleEvent
import com.whatever.caramel.feature.home.mvi.HomeIntent
import com.whatever.caramel.feature.home.mvi.HomeSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToSetting: () -> Unit,
    navigateToStaredCoupleDay: () -> Unit,
    navigateToTodoDetail: (Long, ContentType) -> Unit,
    navigateToCreateTodo: (ContentType) -> Unit,
    showErrorDialog: (String, String?) -> Unit,
    showErrorToast: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToSetting -> navigateToSetting()
                is HomeSideEffect.NavigateToCreateContent -> navigateToCreateTodo(ContentType.CALENDAR)
                is HomeSideEffect.NavigateToContentDetail ->
                    navigateToTodoDetail(
                        sideEffect.contentId,
                        sideEffect.contentType,
                    )

                is HomeSideEffect.NavigateToEditAnniversary -> navigateToStaredCoupleDay()
                is HomeSideEffect.ShowErrorDialog ->
                    showErrorDialog(
                        sideEffect.message,
                        sideEffect.description,
                    )

                is HomeSideEffect.ShowErrorToast -> showErrorToast(sideEffect.message)
                is HomeSideEffect.HideKeyboard -> keyboardController?.hide()
            }
        }
    }

    ObserveLifecycleEvent { event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.intent(HomeIntent.LoadDataOnStart)
        }
    }

    HomeScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
