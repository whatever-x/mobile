package com.whatever.caramel.feature.profile.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun Stepper(
    modifier: Modifier = Modifier,
    currentIndex: Int,
) {
    Row(
        modifier =
            modifier
                .padding(
                    top = CaramelTheme.spacing.s,
                    bottom = CaramelTheme.spacing.xl,
                ),
        horizontalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.xs,
            ),
    ) {
        ProfileCreateStep.entries.forEach { step ->
            Icon(
                painter =
                    painterResource(
                        resource =
                            if (step.ordinal <= currentIndex) {
                                Resources.Icon.ic_progress_dot_active_18
                            } else {
                                Resources.Icon.ic_progress_dot_inactive_18
                            },
                    ),
                tint = Color.Unspecified,
                contentDescription = null,
            )
        }
    }
}
