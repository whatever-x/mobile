package com.whatever.caramel.feat.sample.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SampleRoute(
    viewModel: SampleViewModel = koinViewModel()
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()

    SampleScreen(
        state = uiState.value
    )
}

@Composable
private fun SampleScreen(
    state: SampleUiState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.text,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}