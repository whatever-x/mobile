package com.whatever.caramel.feature.calendar.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelDateMonthPicker
import com.whatever.caramel.core.ui.picker.model.DateUiState
import com.whatever.caramel.feature.calendar.dimension.CalendarDimension
import kotlinx.datetime.Month
import kotlinx.datetime.number
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun CurrentDateMenu(
    modifier: Modifier = Modifier,
    year: Int,
    month: Month,
    isShowDropMenu: Boolean,
    onShowDropMenu: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onShowDropMenu
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${year}.${month.number}",
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary
            )
            Spacer(modifier = Modifier.size(size = CaramelTheme.spacing.xs))
            Icon(
                painter = if (isShowDropMenu) {
                    painterResource(Resources.Icon.ic_arrow_up_16)
                } else {
                    painterResource(Resources.Icon.ic_arrow_down_16)
                },
                contentDescription = null,
                tint = CaramelTheme.color.icon.primary
            )
        }
    }
}

@Composable
internal fun CalendarDatePicker(
    modifier: Modifier = Modifier,
    year: Int,
    month: Month,
    isShowDropMenu: Boolean,
    onDismiss: (Int, Int) -> Unit,
) {
    var selectedYear by remember { mutableStateOf(year) }
    var selectedMonth by remember { mutableStateOf(month.number) }

    AnimatedVisibility(
        modifier = modifier,
        visible = isShowDropMenu,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CaramelTheme.color.alpha.primary)
                .padding(top = CalendarDimension.datePickerHeight)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = {
                        onDismiss(selectedYear, selectedMonth)
                    }
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = CaramelTheme.color.background.primary,
                    shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
                )
                .padding(top = CaramelTheme.spacing.s, bottom = CaramelTheme.spacing.l)
        ) {
            CaramelDateMonthPicker(
                modifier = Modifier.align(Alignment.TopCenter),
                dateUiState = DateUiState(
                    year = year,
                    month = month.number,
                    day = 1
                ),
                onYearChanged = { selectedYear = it },
                onMonthChanged = { selectedMonth = it }
            )
        }
    }
}

