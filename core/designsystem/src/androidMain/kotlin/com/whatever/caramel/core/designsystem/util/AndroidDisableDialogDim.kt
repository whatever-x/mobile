package com.whatever.caramel.core.designsystem.util

import android.view.Gravity
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider

@Composable
actual fun DisableDialogDim() {
    val window = (LocalView.current.parent as? DialogWindowProvider)?.window ?: return
    window.apply {
        setDimAmount(0f)
        setGravity(Gravity.BOTTOM)
        addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }
}