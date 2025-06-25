package com.whatever.caramel.feature.copule.invite.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun InviteBottomBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "상대방이 보낸 초대 코드 직접 입력하기",
            style = CaramelTheme.typography.body2.regular,
            color = CaramelTheme.color.text.primary
        )

        Icon(
            painter = painterResource(resource = Resources.Icon.ic_arrow_right_14),
            tint = CaramelTheme.color.icon.primary,
            contentDescription = null
        )
    }
}