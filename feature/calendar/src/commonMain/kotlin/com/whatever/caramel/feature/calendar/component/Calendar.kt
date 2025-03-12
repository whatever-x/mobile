package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.list_days_of_week
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.model.calendar.CalendarModel
import com.whatever.caramel.feature.calendar.mvi.CalendarState
import org.jetbrains.compose.resources.stringArrayResource

@Composable
fun CaramelCalendar(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onClickDay: (Int) -> Unit,
) {
    val totalWeek = state.calcWeekend()
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        for (week in 0 until totalWeek) {
            if (state.isFirstEmptyWeekendEmptyDay(week)) {
                continue
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (weekDay in 0 until CalendarModel.getWeekCnt()) {
                    val day =
                        (week * CalendarModel.getWeekCnt() + weekDay - state.getFirstDayOfWeekendOrdinal() - 1)
                    if (day in 0 until state.calendarDays.size) {
                        CalendarDay(
                            modifier = Modifier.weight(1f),
                            dayState = state.calendarDays[day],
                            onClickDay = { selectDay ->
                                onClickDay(selectDay)
                            }
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayOfWeek() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        stringArrayResource(Res.array.list_days_of_week).forEach { day ->
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