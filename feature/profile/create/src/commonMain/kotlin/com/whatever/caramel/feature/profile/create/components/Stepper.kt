package com.whatever.caramel.feature.profile.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun Stepper(
    modifier: Modifier = Modifier,
    currentIndex: Int
) {
    Row(
        modifier = modifier
            .padding(
                top = 8.dp,
                bottom = 20.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp
        )
    ) {
        ProfileCreateStep.entries.forEach { step ->
            Icon(
                modifier = Modifier.size(size = 18.dp),
                painter = painterResource(
                    resource = if (step.ordinal <= currentIndex) {
                        Resources.Icon.ic_active_step_18
                    } else {
                        Resources.Icon.ic_inactive_step_18
                    }
                ),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}
