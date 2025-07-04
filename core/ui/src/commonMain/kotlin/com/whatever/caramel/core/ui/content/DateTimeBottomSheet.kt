package com.whatever.caramel.core.ui.content

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dragHandle = null,
        containerColor = CaramelTheme.color.background.tertiary,
        scrimColor = CaramelTheme.color.alpha.primary,
        sheetState = sheetState,
        content = content,
    )
}
