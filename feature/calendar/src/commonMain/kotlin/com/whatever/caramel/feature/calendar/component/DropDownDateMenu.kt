package com.whatever.caramel.feature.calendar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun CalendarDropDownDateMenu(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    isShowDropDownMenu: Boolean,
    onClickDropDownMenu: () -> Unit,
) {
    Box(modifier = modifier) {
        Row(
            modifier =
                modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = onClickDropDownMenu,
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = CaramelTheme.spacing.xs,
                alignment = Alignment.Start
            )
        ) {
            Text(
                text = "${currentDate.year}.${currentDate.monthNumber}",
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary,
            )

            Icon(
                painter =
                    if (isShowDropDownMenu) {
                        painterResource(Resources.Icon.ic_arrow_up_16)
                    } else {
                        painterResource(Resources.Icon.ic_arrow_down_16)
                    },
                contentDescription = null,
                tint = CaramelTheme.color.icon.primary,
            )
        }
    }
}
