package com.whatever.caramel.feature.home.components.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.both
import caramel.feature.home.generated.resources.my
import caramel.feature.home.generated.resources.partner
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.content.ContentRole
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ContentRoleChip(role: ContentRole) {
    val chipBackgroundColor =
        when (role) {
            ContentRole.NONE -> Color.Black
            ContentRole.MY -> Color(0xFF009B08) // @ham2174 FIXME : 컬러 토큰 지정시 변경
            ContentRole.PARTNER -> CaramelTheme.color.fill.secondary
            ContentRole.BOTH -> CaramelTheme.color.fill.brand
        }
    val chipText =
        when (role) {
            ContentRole.NONE -> ""
            ContentRole.MY -> stringResource(resource = Res.string.my)
            ContentRole.PARTNER -> stringResource(resource = Res.string.partner)
            ContentRole.BOTH -> stringResource(resource = Res.string.both)
        }

    Box(
        modifier =
            Modifier
                .size(
                    width = 30.dp,
                    height = 20.dp,
                ).background(
                    color = chipBackgroundColor,
                    shape = CaramelTheme.shape.xs,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = chipText,
            style = CaramelTheme.typography.label2.bold,
            color = CaramelTheme.color.text.inverse,
        )
    }
}
