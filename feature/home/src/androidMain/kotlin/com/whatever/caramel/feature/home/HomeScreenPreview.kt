package com.whatever.caramel.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.home.mvi.HomeState

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HoeScreenPreviewProvider::class) data: HomeState
) {
    CaramelTheme {
        HomeScreen(
            state = data,
            onIntent = {}
        )
    }
}

@Preview
@Composable
private fun QuizPreview(
    @PreviewParameter(QuizPreviewData::class) quizType: HomeState
) {
    CaramelTheme {
        HomeScreen(
            state = quizType,
            onIntent = {}
        )
    }
}