package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun DayOfWeekHeader(modifier: Modifier = Modifier) {
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        daysOfWeek.forEach { day ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    style = CaramelTheme.typography.label2.bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}