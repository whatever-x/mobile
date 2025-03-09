package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.arrow_down
import caramel.feature.calendar.generated.resources.arrow_up
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun CalendarYear(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    onYearSelected: (Int, Int) -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable {
            onYearSelected(year, month)
            isOpen = !isOpen
        },
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
