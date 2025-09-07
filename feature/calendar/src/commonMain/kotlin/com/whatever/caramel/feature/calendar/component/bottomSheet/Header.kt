package com.whatever.caramel.feature.calendar.component.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.model.CalendarBottomSheet
import com.whatever.caramel.feature.calendar.util.toUiText
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun BottomSheetScheduleListHeader(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onClickAddSchedule: (LocalDate) -> Unit,
    isToday: Boolean,
    isEmpty: Boolean,
    holidays: List<CalendarBottomSheet.BottomSheetUiModel>,
    anniversaries: List<CalendarBottomSheet.BottomSheetUiModel>,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp),
    ) {
        Box {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    style = CaramelTheme.typography.heading2,
                    color = CaramelTheme.color.text.primary,
                    textAlign = TextAlign.Center,
                    text = "${date.month.number}.${date.dayOfMonth}.",
                )
                Text(
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                    textAlign = TextAlign.Center,
                    text = date.dayOfWeek.toUiText(),
                )
                if (isToday) {
                    Spacer(modifier = Modifier.size(size = CaramelTheme.spacing.xs))
                    Text(
                        modifier =
                            Modifier
                                .background(
                                    color = CaramelTheme.color.fill.brand,
                                    shape = CaramelTheme.shape.m,
                                ).padding(
                                    horizontal = CaramelTheme.spacing.s,
                                    vertical = CaramelTheme.spacing.xxs,
                                ),
                        textAlign = TextAlign.Center,
                        text = "오늘",
                        style = CaramelTheme.typography.label3.bold,
                        color = CaramelTheme.color.text.inverse,
                    )
                }

                if (holidays.isNotEmpty()) {
                    Spacer(modifier = Modifier.size(size = if (isToday) CaramelTheme.spacing.xxs else CaramelTheme.spacing.xs))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.xxs),
                    ) {
                        items(items = holidays) { holiday ->
                            Text(
                                modifier =
                                    Modifier
                                        .background(
                                            color = CaramelTheme.color.fill.labelAccent1,
                                            shape = CaramelTheme.shape.m,
                                        ).padding(
                                            horizontal = CaramelTheme.spacing.s,
                                            vertical = CaramelTheme.spacing.xxs,
                                        ),
                                textAlign = TextAlign.Center,
                                text = holiday.base.mainText,
                                style = CaramelTheme.typography.label3.bold,
                                color = CaramelTheme.color.text.inverse,
                            )
                        }
                    }
                }
            }
            Icon(
                modifier =
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(all = CaramelTheme.spacing.s)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = { onClickAddSchedule(date) },
                        ),
                painter = painterResource(Resources.Icon.ic_plus_20),
                tint = CaramelTheme.color.icon.tertiary,
                contentDescription = null,
            )
        }

        if (anniversaries.isNotEmpty()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = CaramelTheme.spacing.s),
                verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xs),
            ) {
                anniversaries.fastForEach { anniversary ->
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = CaramelTheme.spacing.xs),
                    ) {
                        Icon(
                            painter = painterResource(Resources.Image.img_anniversary),
                            tint = Color.Unspecified,
                            contentDescription = null,
                        )
                        Text(
                            modifier =
                                Modifier
                                    .padding(start = CaramelTheme.spacing.m)
                                    .align(Alignment.CenterVertically),
                            style = CaramelTheme.typography.body2.bold,
                            color = CaramelTheme.color.text.brand,
                            text = "${anniversary.base.mainText}을 축하해!",
                            textAlign = TextAlign.Start,
                        )
                    }
                }
            }
        }

        if (isEmpty) {
            Text(
                modifier =
                    Modifier
                        .padding(top = CaramelTheme.spacing.s, bottom = CaramelTheme.spacing.l),
                text = "등록한 일정이 없어요",
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.tertiary,
            )
        }
    }
}
