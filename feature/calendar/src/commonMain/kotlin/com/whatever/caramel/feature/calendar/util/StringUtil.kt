package com.whatever.caramel.feature.calendar.util

import androidx.compose.runtime.Composable
import caramel.feature.calendar.generated.resources.Res
import caramel.feature.calendar.generated.resources.friday
import caramel.feature.calendar.generated.resources.monday
import caramel.feature.calendar.generated.resources.saturday
import caramel.feature.calendar.generated.resources.sunday
import caramel.feature.calendar.generated.resources.thursday
import caramel.feature.calendar.generated.resources.tuesday
import caramel.feature.calendar.generated.resources.wednesday
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.stringResource

@Composable
fun DayOfWeek.toUiText(): String =
    stringResource(
        when (this) {
            DayOfWeek.MONDAY -> Res.string.monday
            DayOfWeek.TUESDAY -> Res.string.tuesday
            DayOfWeek.WEDNESDAY -> Res.string.wednesday
            DayOfWeek.THURSDAY -> Res.string.thursday
            DayOfWeek.FRIDAY -> Res.string.friday
            DayOfWeek.SATURDAY -> Res.string.saturday
            DayOfWeek.SUNDAY -> Res.string.sunday
        },
    )
