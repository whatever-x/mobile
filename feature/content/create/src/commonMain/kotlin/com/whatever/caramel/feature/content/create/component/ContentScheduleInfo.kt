package com.whatever.caramel.feature.content.create.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.content.create.util.toUiText
import kotlinx.datetime.LocalDateTime

@Composable
internal fun ContentScheduleInfo(
    modifier: Modifier = Modifier,
    leadingText: String,
    dateTimeInfo: LocalDateTime,
    onClickDate: () -> Unit,
    onClickTime: () -> Unit,
    isAllDay: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = leadingText,
            style = CaramelTheme.typography.body2.regular,
            color = CaramelTheme.color.text.primary,
        )

        Row {
            ScheduleDateTimeText(
                dateTime = dateTimeInfo,
                dateTimeType = ScheduleDateTimeType.DATE,
                onClick = onClickDate,
            )
            if (!isAllDay) {
                Spacer(modifier = Modifier.size(size = CaramelTheme.spacing.xs))
                ScheduleDateTimeText(
                    dateTime = dateTimeInfo,
                    dateTimeType = ScheduleDateTimeType.TIME,
                    onClick = onClickTime,
                )
            }
        }
    }
}

private enum class ScheduleDateTimeType {
    DATE,
    TIME,
}

@Composable
private fun ScheduleDateTimeText(
    modifier: Modifier = Modifier,
    dateTime: LocalDateTime,
    dateTimeType: ScheduleDateTimeType,
    onClick: () -> Unit,
) {
    val content =
        when (dateTimeType) {
            ScheduleDateTimeType.DATE -> {
                val dayOfWeek = dateTime.dayOfWeek.toUiText()
                "${dateTime.year}년 ${dateTime.monthNumber}월 ${dateTime.dayOfMonth}일 ($dayOfWeek)"
            }

            ScheduleDateTimeType.TIME -> {
                val hourText = dateTime.hour.toString().padStart(2, '0')
                val minuteText = dateTime.minute.toString().padStart(2, '0')
                val meridiem = if (dateTime.hour < 12) "오전" else "오후"
                "$meridiem $hourText:$minuteText"
            }
        }
    Text(
        modifier =
            modifier
                .clickable(
                    onClick = onClick,
                    interactionSource = null,
                    indication = null,
                ).background(color = CaramelTheme.color.fill.quaternary, shape = CaramelTheme.shape.xs)
                .padding(vertical = CaramelTheme.spacing.xs, horizontal = 11.dp),
        text = content,
        style = CaramelTheme.typography.body3.regular,
        color = CaramelTheme.color.text.primary,
    )
}
