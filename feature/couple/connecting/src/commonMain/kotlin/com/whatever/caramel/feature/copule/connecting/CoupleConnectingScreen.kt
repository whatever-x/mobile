package com.whatever.caramel.feature.copule.connecting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingIntent
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingState

@Composable
internal fun CoupleConnectingScreen(
    state: CoupleConnectingState,
    onIntent: (CoupleConnectingIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = state.test)
    }
}