package com.whatver.caramel.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun Int.pixelsToDp() = with(LocalDensity.current) { this@pixelsToDp.toDp() }