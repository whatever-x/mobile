package com.whatever.caramel.feature.profile.create.components.step

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelDatePicker
import com.whatever.caramel.core.ui.picker.DateUiState

@Composable
internal fun BirthdayStep(
    modifier: Modifier = Modifier,
    dateUiState: DateUiState,
    onYearChanged: (Int) -> Unit,
    onMonthChanged: (Int) -> Unit,
    onDayChanged: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "생일이\n어떻게 되나요?",
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${dateUiState.year}년 ${dateUiState.month}월 ${dateUiState.day}일",
                style = CaramelTheme.typography.heading2,
                color = CaramelTheme.color.text.primary,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
        ) {
            CaramelDatePicker(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                dateUiState = dateUiState,
                onYearChanged = onYearChanged,
                onMonthChanged = onMonthChanged,
                onDayChanged = onDayChanged,
            )
        }
    }
}