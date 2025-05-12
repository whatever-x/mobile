package com.whatever.caramel.feature.content.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun TitleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val textStyle = CaramelTheme.typography.heading1
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(CaramelTheme.color.fill.brand),
        textStyle = textStyle.copy(
            color = CaramelTheme.color.text.primary
        ),
        singleLine = true
    ) { innerTextField ->
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Box {
                innerTextField()
                if (value.isEmpty()) {
                    Text(
                        text = "제목을 알려주세요",
                        style = textStyle.copy(
                            color = CaramelTheme.color.text.disabledPrimary
                        ),
                    )
                }
            }
        }
    }
}
