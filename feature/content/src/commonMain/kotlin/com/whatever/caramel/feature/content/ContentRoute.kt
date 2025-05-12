package com.whatever.caramel.feature.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.content.mvi.ContentSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ContentRoute(
    viewModel: ContentViewModel = koinViewModel(),
    navigateToBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ContentSideEffect.NavigateToBackStack -> navigateToBackStack()
            }
        }
    }

    ContentScreen(
        state = state,
        onIntent = viewModel::intent
    )
}