package com.whatever.caramel.core.ui.util

import androidx.compose.runtime.Composable
import caramel.core.ui.generated.resources.Res
import caramel.core.ui.generated.resources.friday
import caramel.core.ui.generated.resources.monday
import caramel.core.ui.generated.resources.saturday
import caramel.core.ui.generated.resources.sunday
import caramel.core.ui.generated.resources.thursday
import caramel.core.ui.generated.resources.tuesday
import caramel.core.ui.generated.resources.wednesday
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
