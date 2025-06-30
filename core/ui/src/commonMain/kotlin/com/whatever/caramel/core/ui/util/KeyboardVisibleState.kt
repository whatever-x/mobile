package com.whatever.caramel.core.ui.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalDensity

@Composable
fun rememberKeyboardVisibleState(visiblePixelThreshold: Int = 300): State<Boolean> {
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    val keyboardVisible = remember { mutableStateOf(false) }

    LaunchedEffect(ime) {
        snapshotFlow { ime.getBottom(density) }
            .collect { bottom ->
                keyboardVisible.value = bottom > visiblePixelThreshold
            }
    }

    return keyboardVisible
}
