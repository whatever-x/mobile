package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.calendar.mvi.BottomSheetState

@Composable
internal fun CaramelBottomSheetHandle(
    modifier : Modifier = Modifier,
    bottomSheetState : BottomSheetState
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(13.dp)
                .size(width = 60.dp, height = 4.dp)
                .background(
                    color = CaramelTheme.color.fill.quaternary,
                    shape = CaramelTheme.shape.s
                )
        )
        if(bottomSheetState == BottomSheetState.PARTIALLY_EXPANDED){
            Text(
                style = CaramelTheme.typography.label1.regular,
                color = CaramelTheme.color.text.tertiary,
                text = "우리의 할 일"
            )
        }
    }
}