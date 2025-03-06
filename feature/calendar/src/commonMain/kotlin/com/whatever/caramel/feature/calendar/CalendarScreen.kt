package com.whatever.caramel.feature.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feature.calendar.mvi.CalendarIntent
import com.whatever.caramel.feature.calendar.mvi.CalendarState

@Composable
internal fun CalendarScreen(
    state: CalendarState,
    onIntent: (CalendarIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "캘린더 화면 입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier.align(alignment = Alignment.BottomEnd),
            onClick = { onIntent(CalendarIntent.ClickTodoCard) }
        ) {
            Text(
                text = "바텀 시트의 할 일 카드",
                fontSize = 12.sp
            )
        }

        Button(
            modifier = Modifier.align(alignment = Alignment.BottomStart),
            onClick = { onIntent(CalendarIntent.ClickCreateTodoButton) }
        ) {
            Text(
                text = "바텀 시트의 할 일 생성 버튼",
                fontSize = 12.sp
            )
        }

    }
}