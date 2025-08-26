package com.whatever.caramel.feature.calendar.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.picker.CaramelDateMonthPicker
import com.whatever.caramel.core.ui.picker.model.DateUiState

@Composable
internal fun CalendarDatePicker(
    modifier: Modifier = Modifier,
    dateUiState: DateUiState,
    isShowDropDownMenu: Boolean,
    onYearChanged: (Int) -> Unit,
    onMonthChanged: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = isShowDropDownMenu,
            enter = fadeIn(animationSpec = tween(durationMillis = 150)),
            exit = fadeOut(animationSpec = tween(durationMillis = 150)),
        ) {
            Box(
                modifier =
                    Modifier
                        .background(CaramelTheme.color.alpha.primary)
                        .fillMaxSize()
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = onDismiss,
                        ),
            )
        }

        AnimatedVisibility(
            visible = isShowDropDownMenu,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = CaramelTheme.color.background.primary,
                            shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp),
                        )
                        .padding(
                            top = CaramelTheme.spacing.s,
                            bottom = CaramelTheme.spacing.l
                        ),
            ) {
                CaramelDateMonthPicker(
                    modifier = Modifier.align(Alignment.Center),
                    dateUiState = dateUiState,
                    onYearChanged = { onYearChanged(it) },
                    onMonthChanged = { onMonthChanged(it) },
                )
            }
        }
    }
}
