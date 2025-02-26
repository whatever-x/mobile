package com.whatever.caramel.feat.main.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.main.presentation.home.mvi.HomeIntent
import com.whatever.caramel.feat.main.presentation.home.mvi.HomeState

@Composable
internal fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            onClick = { onIntent(HomeIntent.ClickSettingButton) }
        ) {
            Text(
                text = "셋팅버튼",
                fontSize = 12.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(space = 5.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(HomeIntent.ClickStartedCoupleDayButton) }
            ) {
                Text(
                    text = "커플 시작일 버튼",
                    fontSize = 12.sp
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(HomeIntent.ClickCreateTodoItem) }
            ) {
                Text(
                    text = "오늘 할일 만들기 버튼",
                    fontSize = 12.sp
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(HomeIntent.ClickTodoItem) }
            ) {
                Text(
                    text = "일정 상세 보기 버튼",
                    fontSize = 12.sp
                )
            }
        }
    }
}