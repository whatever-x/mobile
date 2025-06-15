package com.whatever.caramel.error

import androidx.compose.runtime.Composable
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout

@Composable
internal fun ErrorDialog(
    title : String,
    message : String? = null,
    popBackStack : () -> Unit,
) {
    CaramelDialog(
        show = true,
        title = title,
        message = message,
        mainButtonText = "확인",
        onMainButtonClick = popBackStack,
        onDismissRequest = popBackStack
    ) {
        DefaultCaramelDialogLayout()
    }
}