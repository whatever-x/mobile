package com.whatever.caramel.feature.profile.create.components.step

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun NicknameStep(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameChange: (String) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "사용할 닉네임을\n알려주세요",
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.s))

        Text(
            text = "최대 8글자",
            style = CaramelTheme.typography.body3.regular,
            color = CaramelTheme.color.text.secondary,
        )

        BasicTextField(
            value = nickname,
            onValueChange = onNicknameChange,
            cursorBrush = SolidColor(CaramelTheme.color.fill.brand),
            textStyle =
                CaramelTheme.typography.heading2.copy(
                    color = CaramelTheme.color.text.primary,
                    textAlign =
                        if (nickname.isNotEmpty()) {
                            TextAlign.Center
                        } else {
                            TextAlign.Start
                        },
                ),
            singleLine = true,
        ) { innerTextField ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Box {
                    innerTextField.invoke()

                    if (nickname.isEmpty()) {
                        Text(
                            text = "연인간의 애칭도 좋아요",
                            style = CaramelTheme.typography.heading2,
                            color = CaramelTheme.color.text.placeholder,
                        )
                    }
                }
            }
        }
    }
}
