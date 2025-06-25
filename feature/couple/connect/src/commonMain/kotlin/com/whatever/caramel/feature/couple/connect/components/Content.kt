package com.whatever.caramel.feature.couple.connect.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun CoupleConnectContents(
    modifier: Modifier = Modifier,
    code: String,
    onCodeChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "받은 초대 코드를\n입력해 주세요",
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center
        )

        BasicTextField(
            value = code,
            onValueChange = onCodeChange,
            cursorBrush = SolidColor(CaramelTheme.color.fill.brand),
            textStyle = CaramelTheme.typography.heading2.copy(
                color = CaramelTheme.color.text.primary,
                textAlign = if (code.isNotEmpty()) {
                    TextAlign.Center
                } else {
                    TextAlign.Start
                }
            ),
            singleLine = true,
        ) { innerTextField ->
            SubcomposeLayout { constraints ->
                val placeHolderPlaceable = subcompose("placeHolder") {
                    PlaceHolder()
                }.first().measure(constraints)

                val placeHolderWidth = placeHolderPlaceable.width

                val decorationBox = subcompose("decorationBox") {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .then(
                                    if (code.isNotEmpty()) {
                                        Modifier
                                    } else {
                                        Modifier.width(width = placeHolderWidth.toDp())
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            innerTextField.invoke()

                            if (code.isEmpty()) {
                                PlaceHolder()
                            }
                        }
                    }
                }.first().measure(constraints)

                layout(decorationBox.width, decorationBox.height) {
                    decorationBox.placeRelative(0, 0)
                }
            }
        }
    }
}

@Composable
private fun PlaceHolder() {
    Text(
        text = "코드 입력",
        style = CaramelTheme.typography.heading2,
        color = CaramelTheme.color.text.placeholder
    )
}