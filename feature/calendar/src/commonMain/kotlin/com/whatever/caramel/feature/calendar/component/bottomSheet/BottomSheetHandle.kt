package com.whatever.caramel.feature.calendar.component.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.dimension.CalendarDimension

@Composable
internal fun CaramelBottomSheetHandle(
    modifier: Modifier = Modifier,
    topDescVisibility: Boolean,
    onPressSheetHandle: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = CaramelTheme.color.background.tertiary)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { onPressSheetHandle() },
                    )
                },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(13.dp)
                    .size(
                        width = CalendarDimension.sheetHandleWidth,
                        height = CalendarDimension.sheetHandleHeight,
                    ).background(
                        color = CaramelTheme.color.fill.quaternary,
                        shape = CaramelTheme.shape.s,
                    ),
        )
        if (topDescVisibility) {
            Text(
                modifier = Modifier.padding(bottom = CalendarDimension.sheetPartiallyExpandedTextHeight),
                style = CaramelTheme.typography.label1.regular,
                color = CaramelTheme.color.text.tertiary,
                text = "우리의 할 일",
            )
        }
    }
}
