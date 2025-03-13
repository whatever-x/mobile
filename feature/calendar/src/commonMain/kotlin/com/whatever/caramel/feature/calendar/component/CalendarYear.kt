package com.whatever.caramel.feature.calendar.component

import UiText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.arrow_down
import caramel.feature.calendar.generated.resources.arrow_up
import caramel.feature.calendar.generated.resources.month
import caramel.feature.calendar.generated.resources.year
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.mvi.CalendarDatePickerState
import com.whatver.caramel.core.ui.component.Picker
import org.jetbrains.compose.resources.painterResource

@Composable
fun CalendarYearText(
    modifier: Modifier = Modifier,
    state: CalendarDatePickerState,
    onClickDate: (Int, Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null
            ) {
                onClickDate(state.selectedYear!!, state.selectedMonth!!)
            },
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${state.selectedYear}.${state.selectedMonth}",
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically),
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary
            )
            Spacer(modifier = Modifier.padding(CaramelTheme.spacing.xs))
            val vectorRes = if (state.isOpen) {
                Res.drawable.arrow_up
            } else {
                Res.drawable.arrow_down
            }
            Icon(
                painter = painterResource(vectorRes),
                contentDescription = null
            )
        }
    }
}


@Composable
fun CalendarDatePicker(
    modifier: Modifier = Modifier,
    state: CalendarDatePickerState,
    space: Int,
    onClickDatePickerBackground: (Int, Int) -> Unit,
) {
    var selectedYear by remember { mutableStateOf(state.selectedYear) }
    var selectedMonth by remember { mutableStateOf(state.selectedMonth) }

    val yearRange = 1900..2100
    val monthRange = 1..12

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CaramelTheme.color.dim.primary)
            .clickable(
                indication = null,
                interactionSource = null
            ) {
                onClickDatePickerBackground(selectedYear!!, selectedMonth!!)
            }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CaramelTheme.color.background.primary,
                shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
            )
            .pointerInput(Unit) {}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = space.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Picker(
                modifier = Modifier.widthIn(100.dp),
                startIndex = yearRange.indexOf(selectedYear),
                selectedTextStyle = CaramelTheme.typography.heading1,
                selectedTextColor = CaramelTheme.color.text.primary,
                textStyle = CaramelTheme.typography.heading3,
                textColor = CaramelTheme.color.text.tertiary,
                items = yearRange.map {
                    "${it}${
                        UiText.StringResourceId(Res.string.year).asString()
                    }"
                },
            ) { selectedItem ->
                if (selectedItem.isNotEmpty()) {
                    selectedYear = selectedItem.replace(
                        oldValue = UiText.StringResourceId(Res.string.year).asString(),
                        newValue = ""
                    ).toInt()
                }
            }

            Picker(
                modifier = Modifier.widthIn(100.dp),
                startIndex = monthRange.indexOf(selectedMonth),
                selectedTextStyle = CaramelTheme.typography.heading1,
                selectedTextColor = CaramelTheme.color.text.primary,
                textStyle = CaramelTheme.typography.heading3,
                textColor = CaramelTheme.color.text.tertiary,
                items = monthRange.map {
                    "${it}${
                        UiText.StringResourceId(Res.string.month).asString()
                    }"
                },
            ) { selectedItem ->
                if (selectedItem.isNotEmpty()) {
                    selectedMonth = selectedItem.replace(
                        oldValue = UiText.StringResourceId(Res.string.month).asString(),
                        newValue = ""
                    ).toInt()
                }
            }
        }
    }
}

