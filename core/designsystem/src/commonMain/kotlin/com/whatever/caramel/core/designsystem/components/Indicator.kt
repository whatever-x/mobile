package com.whatever.caramel.core.designsystem.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.CaramelPullToRefreshIndicator(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    state: PullToRefreshState
) {
    Box(
        modifier = modifier
            .align(alignment = Alignment.TopCenter)
            .pullToRefreshIndicator(
                state = state,
                isRefreshing = isRefreshing,
                containerColor = Color.Unspecified,
                elevation = 0.dp
            )
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = 300)
        ) { refreshing ->
            if (refreshing) {
                CircularProgressIndicator(
                    color = CaramelTheme.color.fill.brand,
                    trackColor = CaramelTheme.color.fill.brand.copy(alpha = 0.3f),
                    strokeWidth = 5.dp,
                    strokeCap = StrokeCap.Round
                )
            } else {
                CircularProgressIndicator(
                    progress = { state.distanceFraction },
                    color = CaramelTheme.color.fill.brand,
                    trackColor = CaramelTheme.color.fill.brand.copy(alpha = 0.3f),
                    strokeWidth = 5.dp,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}