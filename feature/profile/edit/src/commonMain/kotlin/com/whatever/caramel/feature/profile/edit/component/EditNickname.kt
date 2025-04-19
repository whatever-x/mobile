package com.whatever.caramel.feature.profile.edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.validator.UserValidator
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun EditNickname(
    modifier: Modifier = Modifier,
    nickname: String,
    onNicknameChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xl)
    ) {
        Icon(
            painter = painterResource(Resources.Icon.ic_nickname_36),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "사용할 닉네임을\n알려주세요",
                textAlign = TextAlign.Center,
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary
            )
            Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.xs))
            Text(
                text = "최대 ${UserValidator.NICKNAME_MAX_LENGTH}글자",
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.secondary
            )
        }

        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nickname,
            onValueChange = onNicknameChange,
            cursorBrush = SolidColor(CaramelTheme.color.fill.brand),
            textStyle = CaramelTheme.typography.heading2.copy(
                color = CaramelTheme.color.text.primary,
                textAlign = if (nickname.isNotEmpty()) {
                    TextAlign.End
                } else {
                    TextAlign.Start
                }
            ),
            singleLine = true
        ) { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box (modifier = Modifier.wrapContentWidth()){
                    innerTextField()
                    if (nickname.isEmpty()) {
                        Text(
                            text = "연인간의 애칭도 좋아요",
                            style = CaramelTheme.typography.heading2,
                            color = CaramelTheme.color.text.placeholder
                        )
                    }
                }
            }
        }
    }
}
