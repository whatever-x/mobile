package com.whatever.caramel.feature.profile.edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelDatePicker
import com.whatever.caramel.core.ui.picker.DateUiState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun EditBirthday(
    modifier: Modifier = Modifier,
    dateUiState: DateUiState,
    onYearChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
    onDayChange: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Resources.Icon.ic_gift_36),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            text = "생일을\n알려주세요",
            textAlign = TextAlign.Center,
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary
        )

        Text(
            modifier = Modifier
                .padding(vertical = CaramelTheme.spacing.xl),
            text = "${dateUiState.year}년 ${dateUiState.month}월 ${dateUiState.day}일",
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.primary
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
        ) {
            CaramelDatePicker(
                modifier = Modifier.align(Alignment.BottomCenter),
                dateUiState = dateUiState,
                onYearChanged = onYearChange,
                onMonthChanged = onMonthChange,
                onDayChanged = onDayChange,
            )
        }
    }
}