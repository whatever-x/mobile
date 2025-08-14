package com.whatever.caramel.feature.content.create.util

import androidx.compose.runtime.Composable
import caramel.core.designsystem.generated.resources.Res
import caramel.core.designsystem.generated.resources.day_of_week
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.stringArrayResource

@Composable
fun DayOfWeek.toUiText(): String {
    val dayOfWeek = stringArrayResource(Res.array.day_of_week)
    return dayOfWeek[this.ordinal]
}
