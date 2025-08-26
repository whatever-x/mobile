package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.day_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.stringArrayResource

enum class CalendarType {
    HORIZONTAL,
    VERTICAL,
    ;
}

@Composable
internal fun CaramelCalendar(
    modifier: Modifier = Modifier,
    type: CalendarType,
    pagerState: PagerState,
) {
    Column(modifier = modifier) {
        DayOfWeek(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = CaramelTheme.spacing.s,
                    bottom = CaramelTheme.spacing.m
                )
        )

        when (type) {
            CalendarType.VERTICAL -> {
                // TODO : 세로로 스크롤 가능한 캘린더 생각 해보기
            }

            CalendarType.HORIZONTAL -> {
                HorizontalCalendar(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                )
            }
        }
    }
}

@Composable
private fun DayOfWeek(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        stringArrayResource(Res.array.day_of_week).forEach { dayOfWeek ->
            Text(
                text = dayOfWeek,
                style = CaramelTheme.typography.label2.bold,
                color = CaramelTheme.color.text.primary,
                textAlign = TextAlign.Center,
            )
        }
    }
}