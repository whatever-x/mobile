package com.whatever.caramel.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.whatever.caramel.core.designsystem.animation.SlideInSlideOutSnackbarHost
import com.whatever.caramel.core.designsystem.foundations.Neutral800
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 스낵바를 전역에서 접근하기 위해 compositionLocalOf를 사용하여 스낵바 호스트 상태를 제공합니다.
 * @author GunHyung-Ham
 * @since 2025.03.31
 */
val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }

/**
 * 스낵바를 제어하기 위한 호스트 컴포저블입니다.
 * @author GunHyung-Ham
 * @since 2025.03.31
 */
@Composable
fun CaramelSnackBarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
    snackbar: @Composable (SnackbarData) -> Unit,
) {
    val currentSnackbarData = hostState.currentSnackbarData

    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration =
                when (currentSnackbarData.visuals.duration) {
                    SnackbarDuration.Indefinite -> Long.MAX_VALUE
                    SnackbarDuration.Long -> 10000L
                    SnackbarDuration.Short -> 3000L
                }

            delay(duration)

            currentSnackbarData.dismiss()
        }
    }

    SlideInSlideOutSnackbarHost(
        current = hostState.currentSnackbarData,
        modifier = modifier,
        content = snackbar,
    )
}

@Composable
fun CaramelSnackbar(
    modifier: Modifier = Modifier,
    snackbarData: SnackbarData,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = Neutral800,
                    shape = CaramelTheme.shape.xl,
                )
                .padding(
                    horizontal = CaramelTheme.spacing.xl,
                    vertical = CaramelTheme.spacing.m,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = snackbarData.visuals.message,
            color = CaramelTheme.color.text.inverse,
            style = CaramelTheme.typography.body3.regular,
            textAlign = TextAlign.Center,
        )
    }
}

fun showSnackbarMessage(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
) {
    coroutineScope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(message = message)
    }
}
