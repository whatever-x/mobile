package com.whatever.caramel.feature.profile.edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.whatever.caramel.core.ui.picker.model.DateUiState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun EditStartDate(
    modifier: Modifier = Modifier,
    dateUiState: DateUiState,
    onYearChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
    onDayChange: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = CaramelTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xl)
    ) {
        Icon(
            modifier = Modifier.size(width = 68.dp, height = 32.dp),
            painter = painterResource(Resources.Image.img_couple_on_ground),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            text = "언제부터 사귀기\n시작했나요?",
            textAlign = TextAlign.Center,
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary
        )
        Text(
            modifier = Modifier.padding(vertical = CaramelTheme.spacing.xl),
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