package com.whatever.caramel.feature.home.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.do_not_update_home_data
import caramel.feature.home.generated.resources.unconnected_couple
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal fun LazyListScope.disconnectedCard() {
    item(key = "DisconnectCard") {
        Box(
            modifier =
                Modifier
                    .padding(horizontal = CaramelTheme.spacing.xl)
                    .padding(top = CaramelTheme.spacing.l)
                    .padding(bottom = CaramelTheme.spacing.s),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = CaramelTheme.color.fill.inverse,
                            shape = CaramelTheme.shape.l,
                        ).border(
                            width = 4.dp,
                            color = CaramelTheme.color.fill.quaternary,
                            shape = CaramelTheme.shape.l,
                        ).padding(horizontal = CaramelTheme.spacing.xl)
                        .padding(top = CaramelTheme.spacing.xxl)
                        .padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(resource = Resources.Icon.ic_alert_32),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.m))

                Text(
                    text = stringResource(resource = Res.string.unconnected_couple),
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                )

                Spacer(modifier = Modifier.height(height = CaramelTheme.spacing.xs))

                Text(
                    text = stringResource(resource = Res.string.do_not_update_home_data),
                    style = CaramelTheme.typography.body3.regular,
                    color = CaramelTheme.color.text.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
