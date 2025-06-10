package com.whatever.caramel.core.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onKeyboardAction: () -> Unit,
    readOnly: Boolean = false,
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
        singleLine = false,
        maxLines = 2,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                onKeyboardAction()
            }
        ),
        readOnly = readOnly,
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
