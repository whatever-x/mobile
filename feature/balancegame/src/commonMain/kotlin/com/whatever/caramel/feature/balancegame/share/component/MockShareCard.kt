package com.whatever.caramel.feature.balancegame.share.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
internal fun MockShareCard(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = CaramelTheme.color.fill.brand)
                .padding(horizontal = CaramelTheme.spacing.xl, vertical = CaramelTheme.spacing.xxl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.l),
    ) {
        Text(
            text = "오늘의 밸런스 게임",
            style = CaramelTheme.typography.label1.bold,
            color = CaramelTheme.color.text.inverse,
        )
        Text(
            text = "평생 한 가지 맛만\n먹어야 한다면?",
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.inverse,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s),
        ) {
            MockChoiceChip(modifier = Modifier.weight(1f), label = "🍫 단짠단짠")
            MockChoiceChip(modifier = Modifier.weight(1f), label = "🌶️ 맵단맵단")
        }
        Text(
            text = "우리 커플의 선택은?",
            style = CaramelTheme.typography.body3.regular,
            color = CaramelTheme.color.text.inverse,
        )
    }
}

@Composable
private fun MockChoiceChip(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier =
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = CaramelTheme.color.fill.inverse)
                .padding(vertical = CaramelTheme.spacing.m),
        text = label,
        style = CaramelTheme.typography.body2.bold,
        color = CaramelTheme.color.text.brand,
        textAlign = TextAlign.Center,
    )
}
