package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatver.caramel.core.ui.component.Picker
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource

@Composable
fun CalendarYear(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    year: Int,
    month: Int
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$year.$month",
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically),
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary
            )
            Spacer(modifier = Modifier.padding(CaramelTheme.spacing.xs))
            val vectorRes = if (isOpen) {
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
    currentYear: Int,
    currentMonth: Int,
    dismiss: (Int, Int) -> Unit
) {
    val yearRange = 1900..2100
    val monthRange = 1..12
    var year by remember { mutableStateOf(currentYear) }
    var month by remember { mutableStateOf(currentMonth) }
    

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CaramelTheme.color.dim.primary)
            .pointerInput(Unit) {
                detectTapGestures {
                    Napier.d { "Year and month selected: $year, $month" }
                    dismiss(year, month)
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    CaramelTheme.color.background.primary,
                    shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
                )
                .pointerInput(Unit) {}
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    30.dp,
                    Alignment.CenterHorizontally
                )
            ) {
                Picker(
                    modifier = Modifier.widthIn(100.dp),
                    startIndex = yearRange.indexOf(currentYear),
                    selectedTextStyle = CaramelTheme.typography.heading1.copy(
                        color = CaramelTheme.color.text.primary,
                    ),
                    textStyle = CaramelTheme.typography.heading3.copy(
                        color = CaramelTheme.color.text.tertiary
                    ),
                    items = yearRange.map { "${it}년" },
                ) { selectedItem ->
                    if(selectedItem.isNotEmpty()){
                        year = selectedItem.replace("년", "").toInt()
                    }
                }

                Picker(
                    modifier = Modifier.widthIn(100.dp),
                    startIndex = monthRange.indexOf(currentMonth),
                    selectedTextStyle = CaramelTheme.typography.heading1.copy(
                        color = CaramelTheme.color.text.primary,
                    ),
                    textStyle = CaramelTheme.typography.heading3.copy(
                        color = CaramelTheme.color.text.tertiary
                    ),
                    items = monthRange.map { "${it}월" },
                ) { selectedItem ->
                    if(selectedItem.isNotEmpty()){
                        month = selectedItem.replace("월", "").toInt()
                    }
                }
            }
        }
    }
}
