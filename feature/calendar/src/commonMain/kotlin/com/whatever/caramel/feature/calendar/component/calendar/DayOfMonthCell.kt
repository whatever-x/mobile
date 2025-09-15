package com.whatever.caramel.feature.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import kotlinx.datetime.DayOfWeek

@Composable
internal fun CalendarDayOfMonthText(
    modifier: Modifier = Modifier,
    dayOfMonth: Int,
    dayOfWeek: DayOfWeek,
    isFocus: Boolean,
    isHoliday: Boolean,
) {
    Box(modifier = modifier) {
        Box(
            modifier =
                Modifier
                    .background(
                        color =
                            if (isFocus) {
                                CaramelTheme.color.fill.primary
                            } else {
                                Color.Unspecified
                            },
                        shape = CaramelTheme.shape.s,
                    ).align(Alignment.Center)
                    .size(24.dp),
        )

        Text(
            modifier =
                Modifier
                    .align(Alignment.Center),
            text = dayOfMonth.toString(),
            style = CaramelTheme.typography.body4.regular,
            color =
                when {
                    isFocus -> CaramelTheme.color.text.inverse
                    dayOfWeek == DayOfWeek.SATURDAY -> CaramelTheme.color.text.labelAccent2
                    isHoliday || dayOfWeek == DayOfWeek.SUNDAY -> CaramelTheme.color.text.labelAccent1
                    else -> CaramelTheme.color.text.primary
                },
        )
    }
}
