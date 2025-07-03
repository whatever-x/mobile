package com.whatever.caramel.core.designsystem.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFilterNotNull
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapTo

@Composable
fun SlideInSlideOutSnackbarHost(
    current: SnackbarData?,
    modifier: Modifier = Modifier,
    content: @Composable (SnackbarData) -> Unit,
) {
    val state = remember { FadeInFadeOutState<SnackbarData?>() }
    if (current != state.current) {
        state.current = current
        val keys = state.items.fastMap { it.key }.toMutableList()
        if (!keys.contains(current)) {
            keys.add(current)
        }
        state.items.clear()
        keys.fastFilterNotNull().fastMapTo(state.items) { key ->
            FadeInFadeOutAnimationItem(key) { children ->
                val isVisible = key == current
                val duration = if (isVisible) SNACK_BAR_FADE_IN_MILLIS else SNACK_BAR_FADE_OUT_MILLIS
                val delay = SNACK_BAR_FADE_OUT_MILLIS + SNACK_BAR_IN_BETWEEN_DELAY_MILLIS
                val animationDelay =
                    if (isVisible && keys.fastFilterNotNull().size != 1) {
                        delay
                    } else {
                        0
                    }
                val offsetY =
                    animatedSlideOffsetY(
                        animation =
                            tween(
                                easing = LinearEasing,
                                delayMillis = animationDelay,
                                durationMillis = duration,
                            ),
                        visible = isVisible,
                        onAnimationFinish = {
                            if (key != state.current) {
                                state.items.removeAll { it.key == key }
                                state.scope?.invalidate()
                            }
                        },
                    )
                val opacity =
                    animatedOpacity(
                        animation =
                            tween(
                                easing = LinearEasing,
                                delayMillis = animationDelay,
                                durationMillis = duration,
                            ),
                        visible = isVisible,
                    )
                Box(
                    modifier =
                        Modifier
                            .offset(y = offsetY.value.dp)
                            .graphicsLayer(alpha = opacity.value)
                            .semantics {
                                liveRegion = LiveRegionMode.Polite
                                dismiss {
                                    key.dismiss()
                                    true
                                }
                            },
                ) {
                    children()
                }
            }
        }
    }
    Box(modifier) {
        state.scope = currentRecomposeScope
        state.items.fastForEach { (item, opacity) -> key(item) { opacity { content(item!!) } } }
    }
}

private class FadeInFadeOutState<T> {
    var current: SnackbarData? = null
    var items = mutableListOf<FadeInFadeOutAnimationItem<T>>()
    var scope: RecomposeScope? = null
}

private data class FadeInFadeOutAnimationItem<T>(
    val key: T,
    val transition: FadeInFadeOutTransition,
)

private typealias FadeInFadeOutTransition = @Composable (content: @Composable () -> Unit) -> Unit

@Composable
private fun animatedOpacity(
    animation: AnimationSpec<Float>,
    visible: Boolean,
    onAnimationFinish: () -> Unit = {},
): State<Float> {
    val alpha = remember { Animatable(if (!visible) 1f else 0f) }
    LaunchedEffect(visible) {
        alpha.animateTo(if (visible) 1f else 0f, animationSpec = animation)
        onAnimationFinish()
    }
    return alpha.asState()
}

@Composable
private fun animatedSlideOffsetY(
    animation: AnimationSpec<Float>,
    visible: Boolean,
    onAnimationFinish: () -> Unit = {},
): State<Float> {
    val offsetY = remember { Animatable(if (visible) 100f else 0f) }
    LaunchedEffect(visible) {
        offsetY.animateTo(
            targetValue = if (visible) 0f else 100f,
            animationSpec = animation,
        )
        onAnimationFinish()
    }
    return offsetY.asState()
}

private const val SNACK_BAR_FADE_IN_MILLIS = 150
private const val SNACK_BAR_FADE_OUT_MILLIS = 150
private const val SNACK_BAR_IN_BETWEEN_DELAY_MILLIS = 0
