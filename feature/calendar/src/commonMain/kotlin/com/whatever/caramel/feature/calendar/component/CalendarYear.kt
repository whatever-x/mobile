package com.whatever.caramel.feature.calendar.component

import UiText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
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
fun CalendarYearWithDropDown(
    modifier: Modifier = Modifier,
    state: CalendarDatePickerState,
    onClickDate: (Int, Int) -> Unit,
    onClickDatePickerBackground: (Int, Int) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        CalendarYearText(
            state = state,
            onClickDate = onClickDate,
        )
        CalendarDatePicker(
            state = state,
            space = 30,
            onClickDatePickerBackground = onClickDatePickerBackground
        )
    }
}


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
            modifier = Modifier,
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


// FIXME : Modifier를 통한 재사용은 UI 컴포넌트(재사용 가능한)에만 적용이 되는가?
// FIXME : 바텀시트처럼 뒷배경이 자연스럽게 들어가는건 안될까?
@Composable
fun CalendarDatePicker(
    state: CalendarDatePickerState,
    space: Int, // FIXME : 해당 부분에서는 고정적으로 30인데 파라미터로 받아야하는 이유가 있을까?
    onClickDatePickerBackground: (Int, Int) -> Unit,
) {
    var selectedYear by remember { mutableStateOf(state.selectedYear) }
    var selectedMonth by remember { mutableStateOf(state.selectedMonth) }

    // FIXME : 이런 형식의 상수들은 컴포저블 안에 넣어도 되는가?
    val yearRange = 1900..2100
    val monthRange = 1..12

    DropdownMenu(
        expanded = state.isOpen,
        onDismissRequest = {},
    ) {
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
                )
            ) {
                Picker(
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
}

